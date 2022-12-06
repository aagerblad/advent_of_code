(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_6/input.txt"
       slurp
       drop-last))

;; Part 1
(reduce (fn [i t]
          (let [i (inc i)]
            (if (= (count t) (count (distinct t)))
              (reduced i)
              i))) 3 (partition 4 1 input))

;; Part 2
(reduce (fn [i t]
          (let [i (inc i)]
            (if (= (count t) (count (distinct t)))
              (reduced i)
              i))) 13 (partition 14 1 input))