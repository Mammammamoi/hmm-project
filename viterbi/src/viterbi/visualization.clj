(ns viterbi.visualization)
(require '[dali.io :as io] '[dali.prefab :as prefab]
 '[dali.layout.align] '[dali.layout.matrix] '[dali.layout.connect]
 '[dali.layout.stack]
  '[dali.syntax :as s])
(use '[clojure.string :only (replace includes?)])

 (def shortEmission (hash-map "X" (hash-map "SA" 1) "wir" (hash-map "NAM" 0.2) "werden" (hash-map "MV" 0.3
 "KOPV" 0.5) "geschickt" (hash-map "ADJ" 0.2 "PART" 0.4) "." (hash-map "SZ" 1)
 "/X" (hash-map "SE" 1)))

(defn foo
  "I don't do a whole lot."
  [& args]
  (println "Visualize the World!"))

(defn escapePunctChar
  "Escapes certain punctuation characters of a text"
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
   (includes? text "]")(replace text #"\]" "&rightinterv")
   :else text))

(defn createPairVec
  "Forms a vector with vectors. These vectors contain themselves vectors, which
  always have the same word the first position. At the second position is one of the
  posttags and at the last position is the corresponding probability."
  [sentence filteredDict pairs pairsList]
  (if (empty? sentence)
     pairsList
  (if (empty? (get filteredDict (first sentence)))
     (createPairVec (rest sentence) filteredDict [] (conj pairsList pairs))
  (createPairVec sentence (update-in filteredDict [(first sentence)]
                           (fn [list] (into (hash-map) (rest list))))
    (conj pairs [(first sentence) (first (keys (get filteredDict (first sentence))))
                  (get-in filteredDict [(first sentence)
                                        (first (keys (get filteredDict (first sentence))))])])
      pairsList))))

(defn countRows
  "Returns the maximum finding the longest vector in matrixVec. The argument
  matrixVec should be a vector created by the  method createPairVec."
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
  "Uses the function f and evaluates the value of two elements in the collection.
  Hence returns a collection which is shorter as coll by one element."
  [f coll]
  (if (= (count coll) 1)
      []
   (concat (vector (f (first coll) (second coll))) (mapTwoElements f (rest coll)))))

(defn text-stack
  "Create a text with multilines"
  [wordPos num]
  [:dali/stack {:direction :down :gap 6}
   [:text {:font-family "Verdana" :font-size 14} wordPos]
   [:text {:font-family "Verdana" :font-size 14} num]])

(defn createNode
  "creates a node of a graph with text in the centre of the node"
  [word pos num]
   [:dali/align {:axis :center}
     [:rect {:id (keyword (str (escapePunctChar word) "|" pos)) :stroke {:width 3} :fill :white} :_ [(* (count (str word "|" pos "(" num ")")) 9) 40]]
     (text-stack (str word "|" pos) (str "" num))])

  (defn connect2Columns
    "creates links between nodes in two columns"
    [firstColumn secColumn bestSeq]
    (if (empty? firstColumn)
      []
    (apply vector (concat (connect2Columns (rest firstColumn) secColumn bestSeq)
     (apply vector (map
                   (fn [x]
                    (if (and (some (fn [y2] (and (= y2 (str (get x 0)"|"(get x 1))) (not (= y2 nil)))) bestSeq)
                         (some (fn [y1] (= y1 (str (get (first firstColumn) 0) "|" (str (get (first firstColumn)1))))) bestSeq))
                       [:dali/connect
                       {:from (keyword (str (escapePunctChar (get (first firstColumn) 0))"|"(get (first firstColumn) 1)))
                        :to (keyword (str (escapePunctChar (get x 0)) "|" (get x 1))) :stroke :red :stroke-width 2.5 :dali/marker-end {:id :sharp :style "fill: red;"}}]
                        [:dali/connect
                        {:from (keyword (str (escapePunctChar (get (first firstColumn) 0))"|"(get (first firstColumn) 1)))
                         :to (keyword (str (escapePunctChar (get x 0)) "|" (get x 1))) :stroke :black :stroke-width 2.5 :dali/marker-end :sharp}]))
                   secColumn))))))

(defn graph
  "Creates a graph. The nodes are rectangles with the words of the sentence
  and their corresponding wordtags"
  [sentence filteredDict bestSeq]
  (into [] (concat [:dali/page
     [:defs
     (prefab/sharp-arrow-marker :sharp)]
    (into [] (concat [:dali/matrix {:position [20 20] :columns (count sentence) :row-gap 10
     :column-gap 20}]
     (let [pairVec (apply vector [] (createPairVec sentence filteredDict [] []))]
        (map (fn [taggedWord] (if (= taggedWord :_) :_
                               (createNode (get taggedWord 0) (get taggedWord 1) (get taggedWord 2))))
         (transposeMatrix pairVec (countRows pairVec 0) 0 [])))))]
       (apply vector (apply concat (mapTwoElements (fn [firstC secC]  (connect2Columns firstC secC bestSeq))
                  (createPairVec sentence filteredDict [] [])))))))

  (defn createVitGraph
    "Creates a node-link-diagram and saves it in a
    text.png document"
    [name sentence filteredDict bestSeq]
    (let [bestSeq (apply vector (map (fn [num]
                    (str (get (apply vector sentence) num) "|" (get (apply vector bestSeq) num))) (range (count bestSeq))))]
    (io/render-svg (graph sentence filteredDict bestSeq) (apply str name ".svg") )
    (io/render-png (graph sentence filteredDict bestSeq) (apply str name ".png"))))
