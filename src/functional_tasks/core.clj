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

(defn other-trinary-conversion [trinary-number]
  "Converts a trinary number into a lazy sequence and calculates the decimal equivalent using functions"
  {:post [#(s/valid? number? %)]}
  (if (s/valid? ::valid-trinary trinary-number)
    (reduce + ;; total the decimal value
      (map-indexed
        (fn [position value] (calculate-value value position))
        (reverse ;;  lazy sequence of trinary digits
          (map #(Character/getNumericValue ^char %) trinary-number))))
    0))   ;; invalid trinary, return 0

;; TASK 2
(defn -main [] (println (other-trinary-conversion "102012")))

