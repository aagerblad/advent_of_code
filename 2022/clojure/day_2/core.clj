(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [util :as util]))

(def input
  (->> "day_2/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"[A-Z]" %))))

(defn matchup-wins [p1 p2]
  (case (mod (- p1 p2) 3)
    2 :win
    1 :lose
    0 :draw))

(def scores {:win 6 :lose 0 :draw 3})

(defn match [[p1 p2] scores]
  (+ p2 (scores (matchup-wins p1 p2))))

;; Part 1
(def mapping
  {"A" 1 "B" 2 "C" 3
   "X" 1 "Y" 2 "Z" 3})

(apply +
       (->>
        input
        (map #(map mapping %))
        (map #(match % scores))))


;; Part 2
(defn new_mapping [[p1 p2]]
  (let [new_p1 (mapping p1)]
    [new_p1
     (util/shifted-mod
      (({"X" dec
         "Y" identity
         "Z" inc}
        p2) new_p1) 3 1)]))

(apply +
       (->>
        input
        (map new_mapping)
        (map #(match % scores))))
