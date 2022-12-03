(ns core
  (:require [clojure.string :as str]))

(defn common-char
  [[s1 & s2]] (reduce
               (fn [_ c]
                 (when (every? #(some #{c} %) s2)
                   (reduced c)))
               nil
               s1))

(defn convert-char-to-prio [c]
  (let [i (int c)] (if (< i 91) (- i 38) (- i 96))))

(def input
  (->> "day_3/input.txt"
       slurp
       str/split-lines
       (map char-array)))

;; Part 1
(def input-part-1
  (map #(split-at (/ (count %) 2) %) input))

(apply +
       (map convert-char-to-prio
            (map common-char input-part-1)))

;; Part 2
(def input-part-2 (partition 3 input))

(apply + (map convert-char-to-prio
              (map common-char input-part-2)))
