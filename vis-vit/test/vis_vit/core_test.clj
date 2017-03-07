(ns vis-vit.core-test
  (:require [clojure.test :refer :all]
            [vis-vit.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail not."
    (is (= 1 1))))

(deftest datastructures
  (testing "All datastructures not visualizing"
   (is (= (createPairVec '("wir" "werden" "geschickt" ".") {"wir" ["NAM"], "werden"
                         ["MV" "KOPV"], "geschickt" ["ADJ" "PART"], "." ["S"]} [] [])
         [[["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]]
         [["geschickt" "ADJ"] ["geschickt" "PART"]] [["." "S"]]] ))
    (is (= (transposeMatrix [ [] [["wir" "NAM"]] [["werden" "MV"] ["werden" "KOPV"]]
                            [["geschickt" "ADJ"] ["geschickt" "PART"]] [["." "S"]]]
             2 0 [])
             [["wir" "NAM"]
              ["werden" "MV"]
              ["geschickt" "ADJ"]
              ["." "S"]

              :_
              ["werden" "KOPV"]
              ["geschickt" "PART"]
              :_]))))
