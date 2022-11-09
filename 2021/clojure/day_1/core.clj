(ns day_1.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(defn read_input [x]
  (map
   edn/read-string
   (str/split-lines
    (slurp x))))

(def input (read_input "day_1/input.txt"))

;; (def input (read_input "day_1/test_input.txt"))

;; Part 1
(count
 (filter identity (map >
                       (drop 1 input)
                       (drop-last 1 input))))

;; Part 2
(def input_2
  (map +
       (drop 2 input)
       (drop 1 (drop-last 1 input))
       (drop-last 2 input)))


(count
 (filter identity (map >
                       (drop 1 input_2)
                       (drop-last 1 input_2))))


