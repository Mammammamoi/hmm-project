(ns viterbi.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

  (def emission (hash-map "er" {"PPRO" 1, "SA" 0.5}, "Andrew" {"NAM" 0.04}, "J." {"NAM" 0.04}, "Viterbi" {"NAM" 0.04}, "Dekodierung" {"NAM" 0.04},
  "Faltungscodes" {"NAM" 0.08}, "Nebenproduk" {"NAM" 0.04}, "Analyse" {"NAM" 0.04}, "Fehlerwahrscheinlichkeit" {"NAM" 0.04},
 "G." {"NAM" 0.04}, "D." {"NAM" 0.04}, "Forney" {"NAM" 0.04}, "Optimalempfänger" {"NAM" 0.04}, "Kanale" {"NAM" 0.04},
 "Viterbi-Algorithmus" {"NAM" 0.04}, "Beispiel" {"NAM" 0.04}, "Handy" {"NAM" 0.04}, "Wireless" {"NAM" 0.04}, "Lan" {"NAM" 0.04},
 "Fehlerkorrektur" {"NAM" 0.04}, "Funkübertragung" {"NAM" 0.04}, "Festplatten" {"NAM" 0.04}, "Aufzeichnung" {"NAM" 0.04},
 "Magnetplatten" {"NAM" 0.04}, "Übertragungsfehler" {"NAM" 0.04}, "wurde" {"MV" 0.5}, "wird" {"MV" 0.5}, "fiel" {"V" 0.333},
 "leitete" {"V" 0.333}, "entstehen" {"V" 0.333},  "entwickelt" {"PTZP" 0.25}, "verzerrte" {"PTZP" 0.25, "ADJ" 0.5},
 "gestörte" {"PTZP" 0.25, "ADJ" 0.5}, "verwendet" {"PTZP" 0.25}, "der" {"ART" 0.7, "SA" 0.5}, "den" {"ART" 0.1}, "einem" {"ART" 0.1},
 "die" {"ART" 0.1}, "quasi" {"ADV" 0.25}, "heutzutage" {"ADV" 0.25}, "ebenso" {"ADV" 0.25, "KONJ" 0.25}, "ebenfalls"  {"ADV" 0.25},
 "von" {"PREP" 0.176}, "zu" {"PREP" 0.176}, "als" {"PREP" 0.06}, "bei" {"PREP" 0.12}, "ab" {"PREP" 0.06}, "daraus"{"PREP" 0.06},
 "für"{"PREP" 0.06}, "her"{"PREP" 0.06}, "in"{"PREP" 0.12}, "da"{"PREP" 0.06}, "auf"{"PREP" 0.06}, "und" {"KONJ" 0.25},
 "da"{"KONJ" 0.25}, "oder"{"KONJ" 0.25}, "," {"PUNKT" 0.333}, "." {"PUNKT" 0.666, "SE" 1}, "/" {"SA" 1}, "//" {"CE" 1} ))

 (def bigram (hash-map "SA" (hash-map "PPRO" 0.5, "ART" 0.5), "SE" (hash-map "ART" 0.5, "CE" 0.5) "ADJ" (hash-map "KONJ" 0.5, "NAM" 0.5),
"ADV" (hash-map "ART" 0.17, "NAM" 0.17,"PREP" 0.67), "ART" (hash-map "NAM" 1), "KONJ" (hash-map "ADJ" 0.111, "ADV" 0.111, "KONJ KONJ" 0.222, "KONJ NAM" 0.111, "KONJ PREP" 0.333,
"PTZP" 0.111), "MV" (hash-map "ADV" 0.5, "NAM" 0.5), "NAM" (hash-map "ADV" 0.083, "ART" 0.083, "KONJ" 0.083, "MV" 0.042,
"NAM" 0.167, "PREP" 0.375, "PTZP" 0.083, "V" 0.083), "PPRO" (hash-map "MV" 0.5, "V" 0.5), "PREP" (hash-map "ADJ" 0.056,
"ADV" 0.056, "SE" 0.056, "ART" 0.333, "NAM" 0.333, "PREP" 0.056, "PTZP" 0.056,"PUNKT" 0.056), "PTZP" (hash-map "KONJ" 0.5,
"NAM" 0.25, "PUNKT" 0.25), "PUNKT" (hash-map "ART" 0.5, "PPRO" 0.5, "SE" 0.666, "CE" 0.2),
"V" (hash-map "ADV" 0.333, "SE" 0.333, "PREP" 0.333 )))

  (defn mapVal
    "Maps the values of a hash-map into a new hash-map
     with a function f"
   [f, oldHashMap]
   (into (hash-map) (for [[key val] oldHashMap] [key (f val)]))
  )

  (defn merge-filter
    [f, firstHashMap, secondHashMap, result]
    (if (= firstHashMap {})
      result
    (let [findKey (key (first firstHashMap))]
      (if (contains? secondHashMap findKey)
       (merge-filter f (dissoc firstHashMap findKey) secondHashMap
       (conj result (vector findKey
         (f  (get firstHashMap findKey) (get secondHashMap findKey)))))
         (merge-filter f (dissoc firstHashMap findKey) secondHashMap result)))
     )
  )

 ;keyWords: Liste von Wörtern, priMapVa: hash-map mit wörtern und POS
 ;biMapVal: hash-map mit POS und POS, maximum: das bisherige größte Wort
 (defn maxVal
   [keyWords, priMapVa, biMapVal, maximum, propSeq]
   (if (empty? keyWords)
    maximum
      (let [newMax (into [] (flatten (vector (first keyWords)
        (apply max-key val
         (mapVal (fn [a] (* a propSeq))
            (merge-filter * (get priMapVa (first keyWords)) biMapVal {"DEF" -1}) ) ) )))]
      ;  (println "maximum nicht gefunden")
      ; (println newMax)
      ; (println maximum)
      (if (> (get newMax 2) (get maximum 2))
        (maxVal (rest keyWords), priMapVa, biMapVal, newMax, propSeq)
        (maxVal (rest keyWords), priMapVa, biMapVal, maximum, propSeq))
      )
    )
   )

  ;wordSquence =  [word POS probability]
  (defn viterPos
    "implementation of the viterbi-algorithm"
   [posMap, biGramMap, wordSeqVec]
   (if (= (first (last wordSeqVec)) ".")
    wordSeqVec
    (let [maxProp (maxVal (keys posMap) posMap
         (get biGramMap (get (last wordSeqVec) 1) )
         (vector "" "" 0.0) (get (last wordSeqVec) 2) )]
    (viterPos posMap, biGramMap, (cons wordSeqVec maxProp))
    )
   )
  )
