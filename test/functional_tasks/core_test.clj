(ns functional-tasks.core-test
  (:require [clojure.test :refer :all]
            [functional-tasks.core :refer :all]))

;; TASK 1
(deftest trinary-conversion-test
  (testing "will return a number"
    (is true (number? (trinary-conversion "101"))))
  (testing "will work with small trinary numbers"
    (is (= 1 (trinary-conversion  "1")))
    (is (= 2 (trinary-conversion  "2")))
    (is (= 3 (trinary-conversion "10")))
    (is (= 14 (trinary-conversion  "112"))))
  (testing "with work with large trinary numbers"
    (is (= 32091(trinary-conversion "1122000120")))
    (is (= 5693068 (trinary-conversion "101201020102101"))))
  (testing "with invalid data"
    (is (= 0 (trinary-conversion 101)))
    (is (= 0 (trinary-conversion "")))
    (is (= 0 (trinary-conversion"7")))
    (is (= 0 (trinary-conversion "abc12")))))
(deftest other-trinary-conversion-test
  (testing "will return a number"
    (is true (number? (other-trinary-conversion "101"))))
  (testing "will work with small trinary numbers"
    (is (= 1 (other-trinary-conversion  "1")))
    (is (= 2 (other-trinary-conversion  "2")))
    (is (= 3 (other-trinary-conversion "10")))
    (is (= 14 (other-trinary-conversion  "112"))))
  (testing "with work with large trinary numbers"
    (is (= 32091(other-trinary-conversion "1122000120")))
    (is (= 5693068 (other-trinary-conversion "101201020102101"))))
  (testing "with invalid data"
    (is (= 0 (trinary-conversion 101)))
    (is (= 0 (other-trinary-conversion "")))
    (is (= 0 (other-trinary-conversion"7")))
    (is (= 0 (other-trinary-conversion "abc12")))))
(deftest calculate-value-test
  (testing "will return a number"
    (is true (number? (calculate-value 1 0))))
  (testing "when passed a 0 as the value, should return back specified values"
    (is (= 0 (calculate-value 0 0)))
    (is (= 0 (calculate-value 0 1)))
    (is (= 0 (calculate-value 0 9)))
    (is (= 0 (calculate-value 0 21))))
  (testing "when passed a 1 as the value, should return back specified values"
    (is (= 1 (calculate-value 1 0)))
    (is (= 3 (calculate-value 1 1)))
    (is (= 19683 (calculate-value 1 9)))
    (is (= 10460353203 (calculate-value 1 21))))
  (testing "when passed a 2 as the value, should return back specified values"
    (is (= 2 (calculate-value 2 0)))
    (is (= 18 (calculate-value 2 2)))
    (is (= 39366 (calculate-value 2 9)))
    (is (= 20920706406 (calculate-value 2 21)))))

;; TASK 2
(deftest convert-codon-test
  (testing "will return a string"
    (is true (string? (convert-codon "AUG"))))
  (testing "will return an empty string for invalid codons"
    (is true (= "" (convert-codon "BAH")))
    (is true (= "" (convert-codon ""))))
  (testing "will return Methionine acid for the codon"
    (is true (= "Methionine" (convert-codon "AUG"))))
  (testing "will return Phenylalanine acid for the codon"
    (is true (= "Phenylalanine" (convert-codon "UUU")))
    (is true (= "Phenylalanine" (convert-codon "UUC"))))
  (testing "will return Leucine acid for the codon"
    (is true (= "Leucine" (convert-codon "UUA")))
    (is true (= "Leucine" (convert-codon "UUG"))))
  (testing "will return Serine acid for the codon"
    (is true (= "Serine" (convert-codon "UCU")))
    (is true (= "Serine" (convert-codon "UCC")))
    (is true (= "Serine" (convert-codon "UCA")))
    (is true (= "Serine" (convert-codon "UCG"))))
  (testing "will return Tyrosine acid for the codon"
    (is true (= "Tyrosine" (convert-codon "UAU")))
    (is true (= "Tyrosine" (convert-codon "UAC"))))
  (testing "will return Cysteine acid for the codon"
    (is true (= "Cysteine" (convert-codon "UGU")))
    (is true (= "Cysteine" (convert-codon "UGC"))))
  (testing "will return STOP for the codon"
    (is true (= "STOP" (convert-codon "UAA")))
    (is true (= "STOP" (convert-codon "UAG")))
    (is true (= "STOP" (convert-codon "UGA")))))
(deftest convert-rna-test
  (testing "will return a lazy sequence"
    (is true (instance? clojure.lang.LazySeq (convert-rna "AUGUUUUCU"))))

  (testing "will return a list of codons"
    (is true (= '("AUG" "UUU" "UCU") (convert-rna "AUGUUUUCU")))
    (is true (= '("AUG" "UUU" "UCU" "AUG") (convert-rna "AUGUUUUCUAUG")))))