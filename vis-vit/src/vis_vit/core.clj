(ns vis-vit.core
  (:gen-class))
(require '[dali.io :as io] '[dali.prefab :as prefab]
 '[dali.layout.align] '[dali.layout.matrix])

 (def shortEmission (hash-map "wir" (hash-map "NAM" 0.2) "werden" (hash-map "MV" 0.3
 "KOPV" 0.5) "geschickt" (hash-map "ADJ" 0.2 "PART" 0.4) "." (hash-map "S" 1)))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Visualize the World!"))

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

(defn createNode
  "creates a node of a graph with text in the centre of the node"
  [word pos]
  (let [text (str word "|" pos)]
   [:dali/align {:axis :center}
     [:rect {:id text :stroke {:width 3} :fill :white} :_ [(* (count text) 9) 20]]
     [:text {:fill :black :font-family "Verdana" :font-size 14} text]]))

  (defn connect2Columns
    "creates links between nodes in two columns"
    [firstColumn secColumn]
    (if (empty? firstColumn)
      []
    (apply vector (concat (connect2Columns (rest firstColumn) secColumn)
     (apply vector (map
                   (fn [x] [:dali/connect
                     {:from (str (get (first firstColumn) 0)"|"(get (first firstColumn) 1))
                      :to (str (get x 0) "|" (get x 1)) :dali/marker-end :sharp}])
                   secColumn))))))

(defn graph
  "Creates a graph. The nodes are rectangles with the words of the sentence
  and their corresponding wordtags"
  [sentence dict]
  (concat [:dali/page
    (into [] (concat [:dali/matrix {:position [20 20] :columns (count sentence) :row-gap 5
     :column-gap 20}]
     (let [pairVec (apply vector [] (createPairVec sentence (filterDict sentence dict {}) [] []))]
        (map (fn [taggedWord] (if (= taggedWord :_) :_
                               (createNode (get taggedWord 0) (get taggedWord 1))))
         (transposeMatrix pairVec (countRows pairVec 0) 0 [])))))]
     (apply vector (map (fn [firstC secC] (if (empty? secC) nil
                                           (connect2Columns firstC secC)))
                  (createPairVec sentence (filterDict sentence dict {}) [] [])
                  (rest (createPairVec sentence (filterDict sentence dict {}) [] []))  )))
  )

  (defn createVitGraph
    "Creates a node-link-diagram and saves it in a
    text.png document"
    [name sentence dict]
    (io/render-svg (graph sentence dict) (apply str name ".svg") )
    (io/render-png (graph sentence dict) (apply str name ".png")))
