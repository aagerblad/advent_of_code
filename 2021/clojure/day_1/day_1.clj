(ns day_1
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (map
            edn/read-string
            (str/split-lines
             (slurp "day_1/input.txt"))))

(def input (map
            edn/read-string
            (str/split-lines
             (slurp "day_1/test_input.txt"))))

;; Part 1
(defn compare [x y]
  (> x y))

(count
 (filter identity (map compare
                       (drop 1 input)
                       (drop-last 1 input))))

;; Part 2
(defn sum_ [x y z]
  (+ x y z))

(def input_2
  (map sum_
       (drop 2 input)
       (drop 1 (drop-last 1 input))
       (drop-last 2 input)))


(count
 (filter identity (map compare
                       (drop 1 input_2)
                       (drop-last 1 input_2))))

