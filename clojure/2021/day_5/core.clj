(ns day_5.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.pprint :as pprint]
            [tupelo.core :as tupelo]
            [util :as util]))

(def input
  (->> "day_5/test_input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map (fn [[a b c d]] [[a b] [c d]]))))

input

(def reduced-draws (filter (fn [[[a b] [c d]]] (or (= a c) (= b d))) input))

(def max-values
  (apply map max (apply concat input)))

max-values

(def board
  (vec (repeat (+ 1 (second max-values)) (vec (repeat (+ 1 (first max-values)) 0)))))

board

(defn calc-direction [[a b] [c d]]
  [(if (= c a) 0 (/ (Math/abs (- c a)) (- c a)))
   (if (= d b) 0 (/ (Math/abs (- d b)) (- d b)))])


(util/print-matrix board)

(defn draw-dot [board [x y]]
  (assoc-in board [x y] (min 9 (+ 1 (get-in board [x y])))))


(defn draw-line [board [sx sy] [ex ey] [dx dy]]
  (if (and (= sx ex) (= sy ey))
    (draw-dot board [ex ey])
    (let [nx (+ sx dx)
          ny (+ sy dy)]
      (draw-line (draw-dot board [sx sy]) [nx ny] [ex ey] [dx dy]))))


(draw-line board [0 8] [5 8] [1 0])

(defn add-line [board draws]
  (if (empty? draws)
    board
    (let [draw (first draws)
          start (first draw)
          end (second draw)
          dir (calc-direction start end)]
      (add-line (draw-line board start end dir) (rest draws)))))

(util/print-matrix
 (add-line board reduced-draws))

()