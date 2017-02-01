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
    (is (= (mapVal (fn [k, v] (* v (* k 2)) {2 0.04000000000000001}) {2 (* 0.04000000000000001 4)})))
    (is (= (merge-filter * {"a" 1 "b" 2 "d" 3 "e" 4 "f" 5} {"a" 4 "b" 5 "c" 6} {}) {"a" 4 "b" 10}))
    (is (=   (maxVal (keys (hash-map "AuxV" 0.0072, "KopV" 0.009))
        0.2
        {"AuxV" 0.2 "KopV" 0.2}
       ["a" 0], (hash-map "AuxV" 0.0072 "KopV" 0.009)) ["KopV" 0.000360]))
    )
   )

   (deftest viterbitest
     (testing "Tests zum Viterbi-Algorithmus"
    (is (= (get-ins {"a" {"ab" 2 "ac" 3} "b" {"ab" 4 "d" 5}} '("a" "b") "ab") (hash-map "b" 4 "a" 2)))
    (is (= (viterPos (keys shortEmission) shortEmission shortBigram shortBigram {"S" 1})
     (vector (hash-map "wir" (hash-map "NAM" 0.06) "werden" (hash-map "MV" 0.0072
     "KOPV" 0.009) "geschickt" (hash-map "ADJ" 0.00036 "PART" 0.000576) "." (hash-map "S" 1))
     shortBigram
     )))
     ))

  ;(viterPos emission bigram []) = [["wort1" "POS" num1] ["wort2" "POS2" num]]
  ; (deftest viterTest
  ;   testing "viterbi-algorithm test"
  ;  (is (= (viterPos emission bigram [])  ))
  ;  )
