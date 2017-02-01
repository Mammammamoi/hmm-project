(ns viterbi.core)

(defn foo
  "I don't do a whole lot. I just make you smile."
  [x]
  (println x "Hello, Universe!"))

  (def shortEmission (hash-map "wir" (hash-map "NAM" 0.2) "werden" (hash-map "MV" 0.3
  "KOPV" 0.5) "geschickt" (hash-map "ADJ" 0.2 "PART" 0.4) "." (hash-map "S" 1)))

  (def shortBigram (hash-map "ADJ" (hash-map "ADJ" 0.2 "MV" 0.1 "KOPV" 0.1 "NAM" 0.4 "PART" 0.4
   "S" 0.1) "MV" (hash-map "ADJ" 0.2 "MV" 0.3 "KOPV" 0.1 "NAM" 0.1 "PART" 0.2 "S" 0.1)
   "KOPV" (hash-map "ADJ" 0.2 "MV" 0.1 "KOPV" 0.1 "NAM" 0.4 "PART" 0.1 "S" 0.1)
   "NAM" (hash-map "ADJ" 0.05 "MV" 0.4 "KOPV" 0.3 "NAM" 0.05 "PART" 0.1 "S" 0.1)
   "PART" (hash-map "ADJ" 0.3 "MV" 0.1 "KOPV" 0.1 "NAM" 0.1 "PART" 0.3 "S" 0.1)
     "S" (hash-map "ADJ" 0.3 "MV" 0.2 "KOPV" 0.1 "NAM" 0.3 "PART" 0.1 "S" 0)   ))

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
   (into (hash-map) (for [[key val] oldHashMap] [key (f key val)]))
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
 ;biMapVal: hash-map mit POS und POS, maximum: [POS mit der größte W-keit]
 (defn maxVal
   [keyPOS, priVal, biMapVal, maximum, probSeq]
   (if (empty? keyPOS)
    maximum
    (let [newProb (vector (first keyPOS)
    (* (* (get biMapVal (first keyPOS)) priVal) (get probSeq (first keyPOS))))]
     (if (> (get newProb 1) (get maximum  1))
       (maxVal (rest keyPOS) priVal biMapVal newProb probSeq)
       (maxVal (rest keyPOS) priVal biMapVal maximum probSeq)
     )
    )
   )

   )

 (defn get-ins
   [hashMap keys key]
   (if (empty? keys)
    {}
     (let [value (get-in hashMap (vector (first keys) key))]
       (conj (get-ins hashMap (rest keys) key) [(first keys) value])))
  )


  ; pathProb (hash-map POS1 maxProb POS2 maxProb)
  ; dicBiVec [hash-map von dic und BiMap]
  (defn viterPos
    "implementation of the viterbi-algorithm"
   [words, dictionary, biGramMap, newBiMap, pathProb]
   (if (empty? words)
     (vector dictionary newBiMap)
    (let [newPathProb (mapVal (fn [k, v] (get (maxVal (keys pathProb) v
    (get-ins biGramMap (keys pathProb) k)
       ["a" 0] pathProb) 1))
      (get dictionary (first words)))]
    (viterPos (rest words), (update dictionary (first words) (fn [a] newPathProb)), biGramMap, biGramMap
    newPathProb)
    )
   )
  )
