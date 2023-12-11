(ns functional-tasks.core
  (:require [clojure.spec.alpha :as s])
  (:require [clojure.math :as math])
)

; TASK 1
(s/def ::valid-trinary
  (s/and string?
         #(re-matches #"^[012]*$" %)))

(defn calculate-value [value position]
  "Calculates the decimal value of a digit at the required position "
  {:pre [(s/assert integer? value)
         (s/assert integer? position)]
   :post [#(s/valid? number? %) ]}
  (long (* value (math/pow 3 position))))

(defn trinary-conversion [trinary-number]
  "Converts a trinary number into a lazy sequence and calculates the decimal equivalent using functions"
  {:post [#(s/valid? number? %)]}
  (if (s/valid? ::valid-trinary trinary-number)
    (->> trinary-number  ;; treading macro to tread result of expression
         (map #(Character/getNumericValue ^char %)) ;; convert to lazy-sequence of digits
         (reverse)
         (map-indexed (fn [position value] (calculate-value value position))) ;; calculate decimal equivalent
         (reduce +))
    0))   ;; invalid trinary, return 0

;; this was my first one that I made, but created a better solution
(defn other-trinary-conversion [trinary-number]
  "Converts a trinary number into a lazy sequence and calculates the decimal equivalent using helper functions"
  {:post [#(s/valid? number? %)]}
  (if (s/valid? ::valid-trinary trinary-number) ;; check it is a valid trinary
    (loop [remaining-trinary (reverse (map #(Character/getNumericValue ^char %) trinary-number)) ;; obtain a lazy sequence
           decimal-number 0
           position 0]
      (if-let [value (first remaining-trinary)]  ;; get the first digit in the remaining values and check if it is not nil
        (recur
          (rest remaining-trinary)
          (+ decimal-number (calculate-value value position))
          (inc position))
        decimal-number))
    0)) ;; invalid trinary, return 0

;; TASK 2
(s/def ::valid-rna (s/and string? #(= 0 (mod (count %) 3))))
(def valid-codons #{"AUG" "UUU" "UUC" "UUA" "UUG" "UCU" "UCC" "UCA" "UCG"
                    "UAU" "UAC" "UGU" "UGC" "UGG" "UAA" "UAG" "UGA"})

(defn convert-codon [codon]
  {:post [#(s/valid? string? %)]}
  (if (contains? valid-codons codon)
    (let [codon-acids-map {
                           :AUG "Methionine"
                           :UUU "Phenylalanine" :UUC "Phenylalanine"
                           :UUA "Leucine" :UUG "Leucine"
                           :UCU "Serine" :UCC "Serine" :UCA "Serine" :UCG "Serine"
                           :UAU "Tyrosine" :UAC "Tyrosine"
                           :UGU "Cysteine" :UGC "Cysteine"
                           :UGG "Tryptophan"
                           :UAA "STOP" :UAG "STOP" :UGA "STOP"}]
      (get codon-acids-map (keyword codon)))
    "")) ;; invalid codon

(defn convert-rna [rna]
  {:post [#(s/valid? seq? %)]}
  (->> rna
    (partition 3)
    (map #(apply str %))))

(defn translate-rna [rna]
  (if (s/valid? ::valid-rna rna)
    (loop [codons (convert-rna rna)
           proteins '()]
      (let [current-protein (convert-codon (first codons))]
        (cond
          (or (empty? codons) (= current-protein "STOP")) proteins
          (or (empty? current-protein) (some #{current-protein} proteins)) (recur (rest codons) proteins)
          :else  (recur (rest codons) (concat proteins [current-protein])))))
    '()))

(defn -main [] (println (translate-rna "AUGUUUUGG")))

