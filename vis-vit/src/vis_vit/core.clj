(ns vis-vit.core
  (:gen-class))
(require '[dali.io :as io] '[dali.prefab :as prefab]
 '[dali.layout.align] '[dali.layout.matrix] '[dali.layout.connect]
  '[dali.syntax :as s])
(use '[clojure.string :only (replace includes?)])

 (def shortEmission (hash-map "X" (hash-map "SA" 1) "wir" (hash-map "NAM" 0.2) "werden" (hash-map "MV" 0.3
 "KOPV" 0.5) "geschickt" (hash-map "ADJ" 0.2 "PART" 0.4) "." (hash-map "SZ" 1)
 "/X" (hash-map "SE" 1)))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Visualize the World!"))

(defn escapePunctChar
  [text]
  (cond
   (includes? text "&") (replace text #"\&" "&amp")
   (includes? text "\"")(replace text #"\"" "&quote")
   (includes? text "\\")(replace text #"\\" "&")
   (includes? text "<")(replace text #"<" "&lt")
   (includes? text ">")(replace text #">" "&gt")
   (includes? text "'")(replace text #"'" "&apos")
   (includes? text "-")(replace text #"-" "&ndash")
   (includes? text ".")(replace text #"\." "&dot")
   (includes? text ",")(replace text #"," "&coma")
   (includes? text "!")(replace text #"\!" "&exclpoint")
   (includes? text "|")(replace text #"\|" "&line")
   (includes? text "%")(replace text #"%" "&permil")
   (includes? text "/")(replace text #"/" "&slash")
   (includes? text ";")(replace text #";" "&pointcom")
   (includes? text "?")(replace text #"\?" "&interr")
   (includes? text "=")(replace text #"=" "&equal")
   (includes? text "(")(replace text #"\(" "&leftbr")
   (includes? text ")")(replace text #"\)" "&rightbr")
   (includes? text "[")(replace text #"\[" "&leftinterv")
   (includes? text "]")(replace text #"\]" "&rightinterv")))

(defn filterDict
  "Filter the entry of the dictionary"
  [sentence dict filteredDict]
  (if (empty? sentence)
     filteredDict
   (filterDict (rest sentence) dict
     (into (hash-map) (concat filteredDict
                       {(first sentence) (into [] (keys (get dict (first sentence))))}))
     )))

(defn createPairVec
  [sentence filteredDict pairs pairsList]
  (if (empty? sentence)
     pairsList
  (if (empty? (get filteredDict (first sentence)))
     (createPairVec (rest sentence) filteredDict [] (conj pairsList pairs))
  (createPairVec sentence (update-in filteredDict [(first sentence)]
                           (fn [list] (into [] (rest list ))))
    (conj pairs [(first sentence) (first (get filteredDict (first sentence)))])
      pairsList)))
)

(defn countRows
  [matrixVec maximum]
  (if (empty? matrixVec)
    maximum
  (countRows (rest matrixVec) (max (count (first matrixVec)) maximum))))

(defn transposeMatrix
  "Transposes the matrix-layout"
  [matrixVec rows count transpMatrix]
  (if (= rows count)
    transpMatrix
   (transposeMatrix matrixVec rows (+ count 1)
    (concat transpMatrix
             (reduce (fn [firstVec secondVec]
             (let [elemSec (get secondVec count)]
                (if (= elemSec nil) (conj firstVec :_)
                (conj  firstVec elemSec))))
             matrixVec )))))

(defn mapTwoElements
  [f coll]
  (if (= (count coll) 1)
      []
   (concat (vector (f (first coll) (second coll))) (mapTwoElements f (rest coll)))))

(defn createNode
  "creates a node of a graph with text in the centre of the node"
  [word pos]
   [:dali/align {:axis :center}
     [:rect {:id (keyword (str (escapePunctChar word) "|" pos)) :stroke {:width 3} :fill :white} :_ [(* (count (str word "|" pos)) 9) 20]]
     [:text {:fill :black :font-family "Verdana" :font-size 14} (str word "|" pos)]])

  (defn connect2Columns
    "creates links between nodes in two columns"
    [firstColumn secColumn]
    (if (empty? firstColumn)
      []
    (apply vector (concat (connect2Columns (rest firstColumn) secColumn)
     (apply vector (map
                   (fn [x] [:dali/connect
                     {:from (keyword (str (escapePunctChar (get (first firstColumn) 0))"|"(get (first firstColumn) 1)))
                      :to (keyword (str (escapePunctChar (get x 0)) "|" (get x 1))) :dali/marker-end :sharp}])
                   secColumn))))))

(defn graph
  "Creates a graph. The nodes are rectangles with the words of the sentence
  and their corresponding wordtags"
  [sentence dict]
  (into [] (concat [:dali/page
     [:defs
     (s/css (str "polyline {fill: none; stroke: black;}\n"))
     (prefab/sharp-arrow-marker :sharp)]
    (into [] (concat [:dali/matrix {:position [20 20] :columns (count sentence) :row-gap 5
     :column-gap 20}]
     (let [pairVec (apply vector [] (createPairVec sentence (filterDict sentence dict {}) [] []))]
        (map (fn [taggedWord] (if (= taggedWord :_) :_
                               (createNode (get taggedWord 0) (get taggedWord 1))))
         (transposeMatrix pairVec (countRows pairVec 0) 0 [])))))]
       (apply vector (apply concat (mapTwoElements (fn [firstC secC]  (connect2Columns firstC secC))
                  (createPairVec sentence (filterDict sentence dict {}) [] []))))
  )))

  (defn createVitGraph
    "Creates a node-link-diagram and saves it in a
    text.png document"
    [name sentence dict]
    ;(println (graph sentence dict)))
    (io/render-svg (graph sentence dict) (apply str name ".svg") )
    (io/render-png (graph sentence dict) (apply str name ".png")))
