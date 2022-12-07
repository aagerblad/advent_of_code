(ns core)

(def input
  (->> "day_6/input.txt"
       slurp
       drop-last))

(defn find-distinct [i t]
  (let [i (inc i)]
    (if (= (count t) (count (distinct t)))
      (reduced i)
      i)))

;; Part 1
(reduce find-distinct 3 (partition 4 1 input))

;; Part 2
(reduce find-distinct 13 (partition 14 1 input))