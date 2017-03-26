(ns viterbi.morph-tags
  (:require [viterbi.morphs :as morphs]))

;;NN
(def suffix_tags
  (set [
				{:lemma "keit" :pos "NN" :position "E"}
				{:lemma "heit" :pos "NN" :position "E"}
				{:lemma "tum" :pos "NN" :position "E"}
				{:lemma "ung" :pos "NN" :position "E"}
				{:lemma "schaft" :pos "NN" :position "E"}
				{:lemma "gang" :pos "NN" :position "E"}
				{:lemma "keit" :pos "NN" :position "E"}
				{:lemma "nis" :pos "NN" :position "E"}
				{:lemma "chen" :pos "NN" :position "E"}
				{:lemma "ling" :pos "NN" :position "E"}
				{:lemma "erich" :pos "NN" :position "E"}
				{:lemma "erchen" :pos "NN" :position "E"}
				{:lemma "ler" :pos "NN" :position "E"}
				{:lemma "el" :pos "NN" :position "E"}
				{:lemma "land" :pos "NN" :position "E"}
				{:lemma "heiten" :pos "NN" :position "E"}
				{:lemma "tümer" :pos "NN" :position "E"}
				{:lemma "ungen" :pos "NN" :position "E"}
				{:lemma "schaften" :pos "NN" :position "E"}
				{:lemma "gänge" :pos "NN" :position "E"}
				{:lemma "keiten" :pos "NN" :position "E"}
				{:lemma "nisse" :pos "NN" :position "E"}

        {:lemma "eln" :position "E" :pos "VVFIN"}
        {:lemma "igen" :position "E" :pos "VVFIN"}
        {:lemma "ern" :position "E" :pos "VVFIN"}
        {:lemma "ieren" :position "E" :pos "VVFIN"}
        {:lemma "isieren" :position "E" :pos "VVFIN"}

        {:lemma "bar" :position "E" :pos "ADJA"}
        {:lemma "en" :position "E" :pos "ADJA"}
        {:lemma "rig" :position "E" :pos "ADJA"}
        {:lemma "ern" :position "E" :pos "ADJA"}
        {:lemma "haft" :position "E" :pos "ADJA"} ;;Abgrenzung zu "schaft"
        {:lemma "ig" :position "E" :pos "ADJA"}
        {:lemma "isch" :position "E" :pos "ADJA"}
        {:lemma "lich" :position "E" :pos "ADJA"}
        {:lemma "los" :position "E" :pos "ADJA"}
        {:lemma "mäßig" :position "E" :pos "ADJA"}
        {:lemma "sam" :position "E" :pos "ADJA"}
        ;;Kardinalzahlen
        {:lemma "erlei" :position "E" :pos "ADJA"}
        {:lemma "fach" :position "E" :pos "ADJA"}
  ])
)

(def praefix_tags
  (set [
        {:lemma "ab" :position "A" :pos "ADJA"}
        {:lemma "aller" :position "A" :pos "ADJA"}
        {:lemma "außer" :position "A" :pos "ADJA"}
        {:lemma "binnen" :position "A" :pos "ADJA"}
        {:lemma "erz" :position "A" :pos "ADJA"}
        {:lemma "ge" :position "A" :pos "ADJA"}
        {:lemma "grund" :position "A" :pos "ADJA"}
        {:lemma "inner" :position "A" :pos "ADJA"}
        {:lemma "miss" :position "A" :pos "ADJA"}
        {:lemma "nach" :position "A" :pos "ADJA"}
        {:lemma "ober" :position "A" :pos "ADJA"}
        {:lemma "über" :position "A" :pos "ADJA"}
        {:lemma "un" :position "A" :pos "ADJA"}
        {:lemma "unter" :position "A" :pos "ADJA"}
        {:lemma "ur" :position "A" :pos "ADJA"}
        {:lemma "vor" :position "A" :pos "ADJA"}
        {:lemma "wider" :position "A" :pos "ADJA"}
        {:lemma "zwischen" :position "A" :pos "ADJA"}
  ])
)


(def circumfix_tags
  (set
    [
      ;; V
      {:pos "VVFIN"{:lemma "be" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "be" :position "A" }{ :lemma "ern" :position "E"} }
      {:pos "VVFIN"{:lemma "en" :position "A" }{ :lemma "ieren" :position "E"} }
      {:pos "VVFIN"{:lemma "ent" :position "A"}{:lemma "ren" :position "E"} }
      {:pos "VVFIN"{:lemma "ent" :position "A"}{:lemma "ern" :position "E"} }
      {:pos "VVFIN"{:lemma "in" :position "A" }{ :lemma "ieren" :position "E"} }
      {:pos "VVFIN"{:lemma "ver" :position "A"}{:lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "ver" :position "A"}{:lemma "eln" :position "E"} }
      {:pos "VVFIN"{:lemma "ver" :position "A"}{:lemma "ern" :position "E"} }
      {:pos "VVFIN"{:lemma "an" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "durch" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "auf" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "hinter" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "über" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "unter" :position "A" }{ :lemma "en" :position "E"} }
      {:pos "VVFIN"{:lemma "er" :position "A" }{ :lemma "en" :position "E"} }

      {:pos "ADJA"{:lemma "ent" :position "A" }{ :lemma "er" :position "E"} }
    	{:pos "ADJA"{:lemma "ent" :position "A" }{ :lemma "es" :position "E"} }
    	{:pos "ADJA"{:lemma "ent" :position "A" }{ :lemma "e" :position "E"} }
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "en" :position "E"} }
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "er" :position "E"} }
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "es" :position "E"} }
      {:pos "ADJA" {:lemma "ent" :position "A" }{ :lemma "er" :position "E"} }
    	{:pos "ADJA"{:lemma "ent" :position "A" }{ :lemma "es" :position "E"} }
    	{:pos "ADJA"{:lemma "ent" :position "A" }{ :lemma "e" :position "E"} }
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "en" :position "E"} }
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "er" :position "E"}}
    	{:pos "ADJA"{:lemma "ver" :position "A" }{ :lemma "es" :position "E"} }
    ]
  )
)


(defn find-in-praefix
  [token]
  (for [item praefix_tags
    :when (re-matches (re-pattern (str (get item :lemma) #"[A-Z]*[a-z]*"))token)]
        (get morphs/morphs (get item :lemma))
  )
)


(defn find-in-suffix
  [token]
  (for [item suffix_tags
    :when (re-matches (re-pattern (str #"[A-Z]*[a-z]*" (get item :lemma)))token)]
     (get morphs/morphs (get item :lemma))
  )
)
