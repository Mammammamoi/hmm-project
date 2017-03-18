(ns viterbi.algorithm
  (:require [viterbi.visualization :as vis])
  (:gen-class))
(use '[clojure.set :only (union)])
(use '[clojure.string :only (split)])

(defn -main
  "I don't do a whole lot. I just make you smile."
  [x]
  (println x "Hello, Universe!"))

  (def shortEmission (hash-map "X" (hash-map "SA" 1) "wir" (hash-map "NAM" 0.2) "werden" (hash-map "MV" 0.3
  "KOPV" 0.5) "geschickt" (hash-map "ADJ" 0.2 "PART" 0.4) "." (hash-map "SZ" 1) "/X" (hash-map "SE" 1)))

  (def shortBigram
(hash-map "ADJ" (hash-map "ADJ" 0.2 "MV" 0.1 "KOPV" 0.1 "NAM" 0.4 "PART" 0.4 "SA" 0 "SE" 0 "SZ" 0.1)
   "MV" (hash-map "ADJ" 0.2 "MV" 0.3 "KOPV" 0.1 "NAM" 0.1 "PART" 0.2 "SZ" 0.1 "SA" 0.1 "SE" 0)
   "KOPV" (hash-map "ADJ" 0.2 "MV" 0.1 "KOPV" 0.1 "NAM" 0.4 "PART" 0.1 "SZ" 0.1 "SA" 0.1 "SE" 0)
   "NAM" (hash-map "ADJ" 0.05 "MV" 0.4 "KOPV" 0.3 "NAM" 0.05 "PART" 0.1 "SZ" 0.1 "SA" 0.1 "SE" 0)
   "PART" (hash-map "ADJ" 0.3 "MV" 0.1 "KOPV" 0.1 "NAM" 0.1 "PART" 0.3 "SZ" 0.1 "SA" 0.1 "SE" 0)
     "SZ" (hash-map "ADJ" 0.3 "MV" 0.2 "KOPV" 0.1 "NAM" 0.3 "PART" 0.1 "SE" 1 "SZ" 0 "SA" 0)
     "SA" (hash-map "NAM" 0.3 "ADJ" 0.3 "MV" 0.2 "KOPV" 0.1 "PART" 0.1 "SE" 0 "SZ" 0 "SA" 1)
     "SE" (hash-map "MV" 0 "KOPV" 0 "NAM" 0 "PART" 0 "SE" 1 "SZ" 0 "SA" 0 "SZ" 0)))

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

(defn filterDict
  "Filter the entry of the dictionary"
  [sentence dict filteredDict]
  (if (empty? sentence)
     filteredDict
   (filterDict (rest sentence) dict
     (into (hash-map) (concat filteredDict
                       {(first sentence) (get dict (first sentence))})))))

  (defn mapVal
    "Maps the values of a hash-map into a new hash-map
     with a function f. The argument withKey specifies if
     the function f calculates the new value with the key or without it."
   [f, oldHashMap, withKey]
   (if (= withKey 1)
     (into (hash-map) (for [[key val] oldHashMap] [key (f key val)]))
   (into (hash-map) (for [[key val] oldHashMap] [key (f val)]))))

 (defn maxVal
   "Returns the maximal probability with the corresponding pos-tag"
   [keyPOS, priVal, biMapVal, maximum, probSeq]
   (if (empty? keyPOS)
     maximum
   (let [newProb (vector (first keyPOS)
          (* (* (get biMapVal (first keyPOS)) priVal) (get probSeq (first keyPOS))))]
      (if (> (get newProb 1) (get maximum  1))
        (maxVal (rest keyPOS) priVal biMapVal newProb probSeq)
      (maxVal (rest keyPOS) priVal biMapVal maximum probSeq)))))

 (defn get-ins
   "Returns the value of a key in specific hash-maps.
   Those specific hash-maps are identified through their keys.
   The argument hashMap contains the hash-map, whose keys have a hash-map
   as value."
   [hashMap keys key]
   (if (empty? keys)
    {}
   (let [value (get-in hashMap (vector (first keys) key))]
       (conj (get-ins hashMap (rest keys) key) [(first keys) value]))))

  (defn viterPos
    "Implementation of the viterbi-algorithm. The argument words should
    be a list of words ordered by their position in the sentence."
   [words, dictionary, biGramMap, backtrackMap, pathProb]
   (if (= (first words) "X")
     (viterPos (rest words) dictionary biGramMap backtrackMap pathProb)
    (if (empty? words)
      (vector dictionary backtrackMap)
    (let [newPathProb (mapVal (fn [k, v]  (maxVal (keys pathProb) v
          (get-ins biGramMap (keys pathProb) k) ["a" 0] pathProb))
          (get dictionary (first words)) 1)]
       (let [newPathForm (into (hash-map) (for [[outPos maxVect] newPathProb]
            [outPos (get maxVect 1)])) biGramSeq (mapVal (fn [vektor] (get vektor 0))
            newPathProb 0)]
          (viterPos (rest words), (update dictionary (first words) (fn [a] newPathForm)),
                    biGramMap, (union backtrackMap biGramSeq), newPathForm))))))

  (defn backtracker
  "Returns a sequence of postags as a hash-map. This hash-maps contains bigrams,
  in wich the keys are the postags. The order of the bigrams is reversed."
   [postags, pos]
  (if (= pos "SA")
   [pos]
  (conj (backtracker postags (get postags pos)) pos)))

  (defn bestSeq
   "Returns the most probable sequence of postags using the methode backtracker."
   [sentence, dict, postags]
   (backtracker postags
     (first (apply max-key val (get (filterDict sentence dict {}) (last sentence))))))

  (defn visualizeViterbi
    "Visualizes the viterbi-algorithm through two graphs. One graph shows the initial
    links of the posttags related to the sentence. The second graph shows the links
    and probabilities after using the viterbi-algorithm."
    [sentence, dict, biGramMap]
    (vis/createVitGraph "InitGraph" sentence (filterDict sentence dict {}) '())
    (let [vitVec (viterPos sentence (filterDict sentence dict {}) biGramMap {} {"SA" 1})]
       (vis/createVitGraph "Best Sequence Graph" sentence (vitVec 0)
       (bestSeq sentence (vitVec 0) (vitVec 1)))))
