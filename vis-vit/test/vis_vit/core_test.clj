(ns vis-vit.core-test
  (:require [clojure.test :refer :all]
            [vis-vit.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail not."
    (is (= 1 1))))

(deftest datastructures
  (testing "All datastructures not visualizing"
   (is (= (createPairVec '("X" "wir" "werden" "geschickt" "." "/X") {"X" ["SA"] "wir" ["NAM"], "werden"
                         ["MV" "KOPV"], "geschickt" ["ADJ" "PART"], "." ["SZ"] "/X" ["SE"]} [] [])
         [[["X" "SA"]] [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]]
         [["geschickt" "ADJ"] ["geschickt" "PART"]] [["." "SZ"]] [["/X" "SE"]]] ))
    (is (= (transposeMatrix [ [] [["X" "SA"]] [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]]
                            [["geschickt" "ADJ"] ["geschickt" "PART"]] [["." "SZ"]] [["/X" "SE"]]]
             2 0 [])
             [["X" "SA"]
              ["wir" "NAM"]
              ["werden" "MV"]
              ["geschickt" "ADJ"]
              ["." "SZ"]
              ["/X" "SE"]

              :_
              :_
              ["werden" "KOPV"]
              ["geschickt" "PART"]
              :_
              :_]))
      (is (= (mapTwoElements * [1 2 3 4]) [2 6 12]))))

  (deftest dataVisualization
    (testing "Creation of Visualization Components"
     (is (= (connect2Columns [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]])
     [[:dali/connect {:from "wir|NAM", :to "werden|MV", :dali/marker-end :sharp}]
     [:dali/connect {:from "wir|NAM", :to "werden|KOPV", :dali/marker-end :sharp}]]
         ))))
