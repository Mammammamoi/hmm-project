(ns viterbi.visualization-test
  (:require [clojure.test :refer :all]
            [viterbi.visualization :refer :all]))

(deftest a-test
  (testing "FIXME, I fail not."
    (is (= 1 1))))

(deftest datastructures
  (testing "All datastructures, that are not directly used for the visualization"
   (is (= (createPairVec '("X" "wir" "werden" "geschickt" "." "/X") shortEmission [] [])
         [[["X" "SA" 1]] [["wir" "NAM" 0.2]] [["werden" "MV" 0.3] ["werden" "KOPV" 0.5]]
         [["geschickt" "ADJ" 0.2] ["geschickt" "PART" 0.4]] [["." "SZ" 1]] [["/X" "SE" 1]]] ))
    (is (= (transposeMatrix [ [] [["X" "SA" 1]] [["wir" "NAM" 0.2]] [["werden" "MV" 0.3] ["werden" "KOPV" 0.5]]
                            [["geschickt" "ADJ" 0.2] ["geschickt" "PART" 0.4]] [["." "SZ" 1]] [["/X" "SE" 1]]]
             2 0 [])
             [["X" "SA" 1]
              ["wir" "NAM" 0.2]
              ["werden" "MV" 0.3]
              ["geschickt" "ADJ" 0.2]
              ["." "SZ" 1]
              ["/X" "SE" 1]

              :_
              :_
              ["werden" "KOPV" 0.5]
              ["geschickt" "PART" 0.4]
              :_
              :_]))
      (is (= (mapTwoElements * [1 2 3 4]) [2 6 12]))))

  (deftest dataVisualization
    (testing "Creation of Visualization Components"
     (is (= (connect2Columns [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]] nil)
     [[:dali/connect {:from (keyword "wir|NAM") , :to (keyword "werden|MV") ,
      :stroke :black :stroke-width 2.5 :dali/marker-end :sharp}]
     [:dali/connect {:from (keyword "wir|NAM") , :to (keyword "werden|KOPV") ,
      :stroke :black :stroke-width 2.5 :dali/marker-end :sharp}]]
         ))))
