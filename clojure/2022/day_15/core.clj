(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [util :as util]))

(def input
  (->> "day_15/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"-?\d+" %))
       (map #(map edn/read-string %))
       (map (fn [[a b c d]] [[a b] [c d]]))))

(defn manhattan-dist [[sx sy] [ex ey]]
  (+ (util/abs (- sx ex)) (util/abs (- sy ey))))

(def rr 2000000)

(defn points-on-result-row [result-row [[sx sy] [bx by]]]
  (let [md (manhattan-dist [sx sy] [bx by])
        dist-left (- md (util/abs (- result-row sy)))]
    (if (>= dist-left 0) [(- sx dist-left) (+ sx dist-left)] [])))


(defn get-points [rr_ input_]
  (let [results (sort (filter not-empty (map #(points-on-result-row rr_ %) input_)))]
    (reduce
     (fn [l [s e]]
       (let [[s_ e_] (last l)
             new-list (cond
                        (<= s e_) (conj (vec (drop-last l)) [s_ (max e e_)])
                        :else (conj l [s e]))]
         new-list))
     [(first results)]
     (rest results))))

;; Part 1
(def points (get-points rr input))

(def unavailable-points (apply + (map (fn [[a b]] (inc (- b a))) points)))

(def beacons
  (->> input
       (map second)
       (filter (fn [[_ a]] (= a rr)))
       (map second)
       set
       count))

(- unavailable-points beacons)


;; Part 2
(def new-result
  (loop [i 0]
    (if (> i 4000000) i
        (let [po (get-points i input)]
          (if (> (count po) 1) [i po] (recur (inc i)))))))

(def y_ (first new-result))
(def x_ (->> new-result
             second
             first
             second
             inc))
(+ (* x_ 4000000) y_)
