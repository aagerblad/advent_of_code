(ns core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [util :as util]))

(defn adjust-tail [[hx hy] [tx ty]]
  (let [dx (- hx tx)
        dy (- hy ty)
        [adx ady] (mapv util/abs [dx dy])]
    (case [adx ady]
      [0  2] [tx (- hy (/ dy ady))]
      [2  0] [(- hx (/ dx adx)) ty]
      [1  2] [hx (- hy (/ dy ady))]
      [2  1] [(- hx (/ dx adx)) hy]
      [2  2] [(- hx (/ dx adx)) (- hy (/ dy ady))]
      [tx ty])))

(def directions
  {\U [0 1]
   \D [0 -1]
   \R [1 0]
   \L [-1 0]})

(defn adjust-head [[head & tail] dir]
  (let [new-head (vec (map + head (directions dir)))]
    (reduce
     (fn [positions seg]
       (conj positions (adjust-tail (last positions) seg)))
     [new-head]
     tail)))

(def input
  (->> "day_9/input.txt"
       slurp
       str/split-lines
       (map #(str/split % #"\s"))
       (map (fn [[a b]] [(first (char-array a)) (edn/read-string b)]))
      ;;  (map #(vec (map edn/read-string %)))
       (#(reduce
          (fn [instruction-list [dir length]]
            (concat instruction-list (repeat length dir)))
          []
          %))))
;; Part 1
(def positions [[0 0] [0 0]])

(count (second
        (reduce
         (fn [[p hist] dir]
           (let [new-pos (adjust-head p dir)]
             [new-pos (conj hist (last new-pos))]))
         [positions #{}]
         input)))

;; Part 2
(def long-positions (vec (repeat 10 [0 0])))

(count (second
        (reduce
         (fn [[p hist] dir]
           (let [new-pos (adjust-head p dir)]
             [new-pos (conj hist (last new-pos))]))
         [long-positions #{}]
         input)))
