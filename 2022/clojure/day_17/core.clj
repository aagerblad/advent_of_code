(ns core
  (:require [clojure.string :as str]
            [util :as util]))

(def input
  (->> "day_17/input.txt"
       slurp
       str/trim
       char-array))

input

(def winds (flatten (for [x input] [x \d])))


(def height 1000000)
(def width 7)
(def mat (util/create-xy-matrix \. width height))
(def rocks [\- \+ \J \I \O])

(defn get-rock [rock-type height]
  (mapv #(mapv + [0 height] %)
        (case rock-type
          \- [[2 0] [3 0] [4 0] [5 0]]
          \+ [[2 1] [3 1] [4 1] [3 2] [3 0]]
          \J [[2 0] [3 0] [4 0] [4 1] [4 2]]
          \I [[2 0] [2 1] [2 2] [2 3]]
          \O [[2 0] [2 1] [3 0] [3 1]])))

(defn reverse-print [m]
  (util/print-xy-matrix (map reverse m)))

(defn draw-rock
  ([mat rock val] (reduce
                   (fn [mat pixel] (assoc-in mat pixel val))
                   mat
                   rock))
  ([mat rock] (draw-rock mat rock "#")))

(defn next-pattern
  [rocks last-rock]
  (nth rocks (mod (inc (.indexOf rocks last-rock)) (count rocks))))

(defn collision?
  [mat rock]
  (some #(= "#" (get-in mat %)) rock))

(defn below-bottom?
  [rock]
  (<= (second (apply map min rock)) -1))

(defn move-rock
  [rock movement]
  (let [new-rock (mapv #(mapv + movement %) rock)]
    (cond
      (<= (first (apply map min new-rock)) -1) rock
      (>= (first (apply map max new-rock)) 7) rock
      :else new-rock)))

(def blow-dir {\> [1 0] \< [-1 0] \d [0 -1]})

(defn get-height
  [rock]
  (second (apply map max rock)))

(get-height (get-rock \J 10))

(loop [mat mat
       move-queue winds
       cur-pattern \-
       cur-rock (get-rock \- 3)
       rocks-fallen 0
       height 0
       new-stone true]
  (cond
    (>= rocks-fallen 2022) (inc height)
    (empty? move-queue) (recur mat
                               winds
                               cur-pattern
                               cur-rock
                               rocks-fallen
                               height
                               false)
    :else
    (let [movement (first move-queue)
          new-rock (move-rock cur-rock (blow-dir movement))
          next-pattern (next-pattern rocks cur-pattern)
          new-height (max height (get-height cur-rock))]
      (cond
        (below-bottom? new-rock)
        (recur (draw-rock mat cur-rock)
               (rest move-queue)
               next-pattern
               (get-rock next-pattern (+ new-height 4))
               (inc rocks-fallen)
               new-height
               true)

        (and (= movement \d) (collision? mat new-rock))
        (recur (draw-rock mat cur-rock)
               (rest move-queue)
               next-pattern
               (get-rock next-pattern (+ new-height 4))
               (inc rocks-fallen)
               new-height
               true)

        (collision? mat new-rock)
        (recur mat
               (rest move-queue)
               cur-pattern
               cur-rock
               rocks-fallen
               height
               false)

        :else
        (recur mat
               (rest move-queue)
               cur-pattern
               new-rock
               rocks-fallen
               height
               false)))))