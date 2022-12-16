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

(def result-row 2000000)

(defn points-on-result-row [[[sx sy] [bx by]]]
  (let [md (manhattan-dist [sx sy] [bx by])
        dist-left (- md (util/abs (- result-row sy)))]
    ;; (println md dist-left sy)
    (if (>= dist-left 0) [(- sx dist-left) (+ sx dist-left)] [])))


(sort (map points-on-result-row input))
(def results (sort (filter not-empty (map points-on-result-row input))))

results


(def points
  (reduce
   (fn [l [s e]]
     (let [[s_ e_] (last l)
           new-list (cond
                      (<= s e_) (conj (vec (drop-last l)) [s_ (max e e_)])
                      :else (conj l [s e]))]
       (println [s e] [s_ e_] new-list)
       new-list))
   [(first results)]
   (rest results)))


(def unavailable-points
  (apply + (map (fn [[a b]] (inc (- b a))) points)))

(def beacons
  (->> input
       (map second)
       (filter (fn [[_ a]] (= a result-row)))
       (map second)
       set
       count))
input
beacons

(- unavailable-points beacons)

;; 

(def adjustment (apply mapv min (apply concat input)))
(def adjustment [-10 -10])
adjustment
(def adjusted-input (mapv (fn [row] (mapv (fn [pair] (mapv - pair adjustment)) row)) input))
(def max-val (mapv #(* 2 %) (mapv inc (apply mapv max (apply concat adjusted-input)))))
(def max-val [40 40])
max-val


(def starting-map (util/create-xy-matrix "." (max-val 0) (max-val 1)))

(util/print-xy-matrix starting-map)

adjusted-input



(defn get-rombus [x-range [sx sy] dist]
  (loop [x (first x-range)
         tx (rest x-range)
         points []
         xi 0
         width 0]
    (let [y-range (range (- sy width) (+ sy width 1))]
      (cond
        (> xi (* 2 dist)) points
        (< xi dist) (recur (first tx) (rest tx) (concat points (for [y y-range] [x y])) (inc xi) (inc width))
        :else (recur (first tx) (rest tx) (concat points (for [y y-range] [x y])) (inc xi) (dec width))))))

(defn draw-rombus [sm [sx sy] dist]
  (let [x-range (range (- sx dist) (+ sx dist 1))
        point-list (get-rombus x-range [sx sy] dist)]
    (reduce
     (fn [sm_ point]
       (assoc-in sm_ point "#"))
     sm
     point-list)))


(util/print-xy-matrix (draw-rombus starting-map [10 10] 2))
(def map-with-blocked-squares
  (reduce
   (fn [m [sensor beacon]]
     (let [dist (manhattan-dist sensor beacon)]
       (draw-rombus m sensor dist)))
   starting-map
   adjusted-input))

(util/print-xy-matrix map-with-blocked-squares 2)

(def map-with-sensors-and-beacons
  (reduce
   (fn [m [sensor beacon]]
     (let [add-sensor (assoc-in m sensor "S")
           add-beacon (assoc-in add-sensor beacon "B")]
       add-beacon))
   map-with-blocked-squares
   adjusted-input))

(util/print-xy-matrix map-with-sensors-and-beacons 2)


