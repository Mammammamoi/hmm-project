(ns viterbi.visualization-test
  (:require [clojure.test :refer :all]
            [viterbi.visualization :refer :all]))

            (def shortEmission (hash-map "<s>" (hash-map "<s>" 1) "wir" (hash-map "PPER" 0.2) "werden" (hash-map "VAINF" 0.3
            "VVFIN" 0.5) "geschickt" (hash-map "ADJD" 0.2 "VVPP" 0.4) "." (hash-map "$." 1) "</s>" (hash-map "</s>" 1)))

(deftest a-test
  (testing "FIXME, I fail not."
    (is (= 1 1))))

(deftest datastructures
  (testing "All datastructures, that are not directly used for the visualization"
   (is (= (createPairVec '("<s>" "wir" "werden" "geschickt" "." "</s>") shortEmission [] [])
          [[["<s>" "<s>" 1]] [["wir" "PPER" 0.2]] [["werden" "VVFIN" 0.5]
          ["werden" "VAINF" 0.3]] [["geschickt" "ADJD" 0.2] ["geschickt" "VVPP" 0.4]]
          [["." "$." 1]] [["</s>" "</s>" 1]]] ))
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
      (is (= (mapTwoElements * [1 2 3 4]) [2 6 12]))
      (is (= (filterColumns [["wir" "NAM"] ["wir" "MV"]] [["werden" "MV"] ["werden" "KOPV"]]
                             {["</s>" "</s>"] ["." "$."], ["wir" "NAM"] ["<s>" "<s>"],
                             ["werden" "MV"] ["wir" "NAM"], ["werden" "KOPV"] ["wir" "NAM"],
                             ["geschickt" "ADJ"] ["werden" "KOPV"], ["geschickt" "PART"] ["werden" "MV"],
                             ["." "$."] ["geschickt" "PART"]})
            [[["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]]]))))

  (deftest dataVisualization
    (testing "Creation of Visualization Components"
     (is (= (connect2Columns [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]] nil)
     [[:dali/connect {:from (keyword "wir|NAM") , :to (keyword "werden|MV") ,
      :stroke :black :stroke-width 2.5 :dali/marker-end :sharp}]
     [:dali/connect {:from (keyword "wir|NAM") , :to (keyword "werden|KOPV") ,
      :stroke :black :stroke-width 2.5 :dali/marker-end :sharp}]]
         ))))
