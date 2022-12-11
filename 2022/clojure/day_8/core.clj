(ns core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [util :as util]))

(def input
  (->> "day_8/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d" %))
       (map #(vec (map edn/read-string %)))
       vec))

(defn visible-trees [m]
  (mapv
   #(first
     (reduce
      (fn [[row highest] tree]
        (if (> tree highest)
          [(conj row 1) tree]
          [(conj row 0) highest]))
      [[] -1]
      %)) m))

(defn mirror-map [m]
  (mapv #(vec (reverse %)) m))

(apply +
       (flatten
        (map #(map max %1 %2 %3 %4)
             (visible-trees input)
             (mirror-map (visible-trees (mirror-map input)))
             (util/transpose (visible-trees (util/transpose input)))
             (util/transpose (mirror-map (visible-trees (mirror-map (util/transpose input))))))))




(defn inc-view [height-vec tree-height]
  (vec (concat
        (repeat (inc tree-height) 1)
        (map inc (take-last (- (count height-vec) tree-height 1) height-vec)))))

(def starting-view (repeat 10 0))

(defn view-from-tree [m]
  (mapv
   #(first
     (reduce
      (fn [[row view] tree]
        [(conj row (nth view tree)) (inc-view view tree)])
      [[] starting-view]
      %)) m))

(apply max
       (flatten
        (map #(map * %1 %2 %3 %4)
             (view-from-tree input)
             (mirror-map (view-from-tree (mirror-map input)))
             (util/transpose (view-from-tree (util/transpose input)))
             (util/transpose (mirror-map (view-from-tree (mirror-map (util/transpose input))))))))