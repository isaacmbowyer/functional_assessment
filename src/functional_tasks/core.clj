(ns functional-tasks.core
  (:require [clojure.spec.alpha :as s]))

; TASK 1
(s/def ::valid-trinary
  (s/and string?
         #(re-matches #"^[012]*$" %)))

(defn convert-string-to-list [number]
  (reverse (map #(Character/getNumericValue ^char %) number)))

(defn convert-trinary-to-decimal [number]
  {:pre [(s/valid? ::valid-trinary number)]}
  (loop [trinary-number-list (convert-string-to-list number)
         decimal-number 0]

  )
)

(defn -main [] ())