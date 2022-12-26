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
(apply +
       (map
        #(- 6 (count (get-adjacent-voxels % input)))
        input))

