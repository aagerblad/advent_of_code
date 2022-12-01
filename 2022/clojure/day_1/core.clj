(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_1/input.txt"
       slurp
       (#(str/split % #"\n\n"))
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))))

;; Part 1
(apply max (map #(apply + %) input))

;; Part 2
(apply + (take 3 (sort > (map #(apply + %) input))))