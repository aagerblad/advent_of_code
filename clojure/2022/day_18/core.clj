(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_18/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))))


(defn get-adjacent-voxels
  [v voxels]
  (filter (fn [a]
            (->> v
                 (map #(abs (- %1 %2)) a)
                 (apply +)
                 (= 1))) voxels))

;; Part 1
(reduce
 #(+ %1 6 (- (count (get-adjacent-voxels %2 input))))
 0
 input)


;; Part 2
(defn val-in?  [v coll] (some #(= % v) coll))

(defn get-adjacent-coords
  [v]
  (let [adjacent [[1 0 0] [-1 0 0]
                  [0 1 0] [0 -1 0]
                  [0 0 1] [0 0 -1]]]
    (for [x adjacent] (map + x v))))

(def max-val (apply max (apply map max input)))

(def min-val (apply min (apply map min input)))

(defn out-of-bounds?
  [v]
  (or (> (apply max v) (inc max-val))
      (< (apply min v) (dec min-val))))

(defn flood-fill
  [space start]
  (loop [queue [start]
         visited #{start}
         surface 0
         counter 0]
    (cond
      (empty? queue) (concat space visited)

      (out-of-bounds? (first queue)) space

      :else
      (let [cur-v (first queue)
            adjacent-voxels (get-adjacent-voxels cur-v space)
            adjacent-coords (get-adjacent-coords cur-v)
            new-visited (conj visited cur-v)
            new-queue (->> adjacent-coords
                           (#(concat % (rest queue)))
                           (remove #(val-in? % new-visited))
                           (remove #(val-in? % adjacent-voxels))
                          ;;  (remove out-of-bounds?)
                           )
            new-surface (+ surface (count adjacent-voxels))]
        (recur new-queue
               new-visited
               new-surface
               (inc counter))))))

(def new-space
  (reduce
   (fn [space cur-v]
    ;;  (println "cur-v" cur-v)
     (let [adjacent-coords (get-adjacent-coords cur-v)
           adjacent-voxels (get-adjacent-voxels cur-v space)
           open-coords (remove #(val-in? % adjacent-voxels) adjacent-coords)]
      ;;  (println adjacent-coords adjacent-voxels open-coords)
       (reduce flood-fill space open-coords)))
   input
   input))

input
new-space

(reduce
 #(+ %1 6 (- (count (get-adjacent-voxels %2 new-space))))
 0
 new-space)

1