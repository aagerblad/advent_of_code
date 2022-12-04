(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_4/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map #(split-at 2 %))))

;; Part 1
(defn a-fully-in-b? [[[x1 x2] [y1 y2]]]
  (and (<= y1 x1) (>= y2 x2)))

(defn full-overlap? [[a b]]
  (or (a-fully-in-b? [a b]) (a-fully-in-b? [b a])))

(count (filter full-overlap? input))

;; Part 2
(defn a-partially-in-b? [[[x1 x2] [y1 y2]]]
  (or (<= y1 x1 y2) (<= y1 x2 y2)))

(defn partial-overlap? [[a b]]
  (or (a-partially-in-b? [a b]) (a-partially-in-b? [b a])))

(count (filter partial-overlap? input))
