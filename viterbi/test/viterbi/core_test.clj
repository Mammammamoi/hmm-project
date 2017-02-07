(ns viterbi.core-test
  (:require [clojure.test :refer :all]
            [viterbi.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

 (deftest maxValtest
   (testing "Tests von Methoden, welche mit der Methode maxVal in vorkommen oder von maxVal selbst"
     (is (= (merge-with * (get {"geschickt" {"Part" 0.4},
      "werden" {"AuxV" 0.3, "KopV" 0.5}} "geschickt")
     {"AuxV" 0.4 "KopV" 0.3 "Part" 0.1})
    {"Part" 0.04000000000000001, "AuxV" 0.4, "KopV" 0.3} ))
    (is (= (mapVal (fn [k, v] (* v (* k 2)) {2 0.04000000000000001}) {2 (* 0.04000000000000001 4)} 1 )))
    (is (=   (maxVal (keys (hash-map "AuxV" 0.0072, "KopV" 0.009))
        0.2
        {"AuxV" 0.2 "KopV" 0.2}
       ["a" 0], (hash-map "AuxV" 0.0072 "KopV" 0.009)) ["KopV" 0.000360]))))

   (deftest viterbitest
     (testing "Tests zum Viterbi-Algorithmus"
    (is (= (get-ins {"a" {"ab" 2 "ac" 3} "b" {"ab" 4 "d" 5}} '("a" "b") "ab") (hash-map "b" 4 "a" 2)))
    (is (= (viterPos '("wir" "werden" "geschickt" ".") shortEmission shortBigram {} {"S" 1})
     (vector {"wir" {"NAM" 0.06}, "geschickt" {"ADJ" 3.6E-4, "PART" 5.760000000000001E-4},
     "werden" {"MV" 0.0072, "KOPV" 0.009}, "." {"S" 5.760000000000002E-5}}
     {"NAM" "S", "MV" "NAM", "KOPV" "NAM", "ADJ" "KOPV", "PART" "MV", "S" "PART"}
     )))))

    (deftest backtrackerTest
      (testing "Tests zur Herausgabe der POS-Tags in richtiger Reihenfolge"
        (is (= (backtracker {"NAM" "S", "MV" "NAM", "KOPV" "NAM", "ADJ" "KOPV", "PART" "MV", "S" "PART"}
          "PART") (vector "S" "NAM" "MV" "PART")))
        (is (= (bestSeq "wir werden geschickt."
           shortEmission
           {"NAM" "S", "MV" "NAM", "KOPV" "NAM", "ADJ" "KOPV", "PART" "MV", "S" "PART"})
           (vector "S" "NAM" "MV" "PART")))))
