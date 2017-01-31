(ns viterbi.core-test
  (:require [clojure.test :refer :all]
            [viterbi.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

 (deftest maxValtest
   (testing "FIXME, I fail."
   (is (= (merge-with * (get {"geschickt" {"Part" 0.4},
   "werden" {"AuxV" 0.3, "KopV" 0.5}} "geschickt")
    {"AuxV" 0.4 "KopV" 0.3 "Part" 0.1})
    {"Part" 0.04000000000000001, "AuxV" 0.4, "KopV" 0.3} ))
   (is (= (select-keys
      (merge-with * (get {"geschickt" {"Part" 0.4},
     "werden" {"AuxV" 0.3, "KopV" 0.5}} "geschickt")
      {"AuxV" 0.4 "KopV" 0.3 "Part" 0.1}) (keys (get {"geschickt" {"Part" 0.4},
      "werden" {"AuxV" 0.3, "KopV" 0.5}} "geschickt"))) {"Part" 0.04000000000000001}))
    (is (= (mapVal (fn [a] (* a 2) {"Part" 0.04000000000000001}) {"Part" (* 0.04000000000000001 2)})))
    (is (= (apply max-key val {"a" 0 "b" 0.1 "c" 0.5}) ["c" 0.5]))
    (is (= (flatten (vector "ach" ["c" 0.5])) ["ach" "c" 0.5]))
    (is (= (merge-filter * {"a" 1 "b" 2 "d" 3 "e" 4 "f" 5} {"a" 4 "b" 5 "c" 6} {}) {"a" 4 "b" 10}))
    (is (=   (maxVal (keys (hash-map "wir" (hash-map "Nomn" 0.2)
       "werden" (hash-map "AuxV" 0.3 "KopV" 0.5)
       "geschickt" (hash-map "Adje" 0.2 "Part" 0.4)))
       (hash-map "wir" (hash-map "Nomn" 0.2)
        "werden" (hash-map "AuxV" 0.3 "KopV" 0.5) "geschickt" (hash-map "Adje" 0.2 "Part" 0.4))
        {"AuxV" 0.4 "KopV" 0.3 "Part" 0.1}
       ["a" "b" 0], 2) ["werden" "KopV" 0.3]))
    )
   )

  ;(viterPos emission bigram []) = [["wort1" "POS" num1] ["wort2" "POS2" num2]]
   (deftest viterTest
     testing "viterbi-algorithm test"
    (is (= (viterPos emission bigram [])  ))
    )
