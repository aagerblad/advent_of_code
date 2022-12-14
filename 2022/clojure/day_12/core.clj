(ns core
  (:require [clojure.string :as str]
            [util :as util]))

(def input
  (->> "day_12/input.txt"
       slurp
       str/split-lines
       (map char-array)
       (mapv #(mapv (fn [x] (case x \S 0 \E 27 (- (int x) 96))) %))))


(def height (count input))
(def width (count (first input)))


(defn sur-h [y] (cond (= y 0) [(inc y)] (>= y (dec height)) [(dec y)] :else [(dec y) (inc y)]))
(defn sur-w [x] (cond (= x 0) [(inc x)] (>= x (dec width)) [(dec x)] :else [(dec x) (inc x)]))

(defn sur [[y x]]
  (vec (concat []
               (for [h (sur-h y)] [h x])
               (for [w (sur-w x)] [y w]))))



(def height-map input)


;; Part 1
(def position-of-start (.indexOf (flatten height-map) 0))
(def start-point [(int (/ position-of-start width)) (mod position-of-start width)])

(def shortest-distance-map (assoc-in (vec (repeat height (vec (repeat width 1000)))) start-point 0))
(def dist-map (loop [next-steps [start-point]
                     sdm shortest-distance-map]
                (if (empty? next-steps)
                  sdm
                  (let [cur-step (first next-steps)
                        cur-val (get-in height-map cur-step)
                        potential-steps (sur cur-step)
                        actual-steps (filter (fn [step] (and (<= (get-in height-map step) (inc cur-val))
                                                             (< (get-in sdm cur-step) (dec (get-in sdm step)))))
                                             potential-steps)]
                    (recur
                     (concat (rest next-steps) actual-steps)
                     (reduce
                      (fn [sdm step] (assoc-in sdm step (inc (get-in sdm cur-step))))
                      sdm
                      actual-steps))))))

(util/print-matrix dist-map 6)
(util/print-matrix height-map 6)
(nth (flatten dist-map) (.indexOf (flatten height-map) 27))


;; Part 2
(def position-of-start-2 (.indexOf (flatten height-map) 27))
(def start-point-2
  [(int (/ position-of-start-2 width)) (mod position-of-start-2 width)])


(def shortest-distance-map-2
  (assoc-in (vec (repeat height (vec (repeat width 1000)))) start-point-2 0))

(def dist-map-2
  (loop [next-steps [start-point-2]
         sdm shortest-distance-map-2]
    (if (empty? next-steps)
      sdm
      (let [cur-step (first next-steps)
            cur-val (get-in height-map cur-step)
            potential-steps (sur cur-step)
            actual-steps (filter
                          (fn [step]
                            (and (>= (get-in height-map step) (dec cur-val))
                                 (< (get-in sdm cur-step) (dec (get-in sdm step)))))
                          potential-steps)]
        (recur
         (concat (rest next-steps) actual-steps)
         (reduce
          (fn [sdm step] (assoc-in sdm step (inc (get-in sdm cur-step))))
          sdm
          actual-steps))))))

(apply min
       (map first (filter (fn [[_ b]] (<= b 1))
                          (map (fn [a b] [a b]) (flatten dist-map-2) (flatten height-map)))))


(util/print-matrix dist-map-2 6)
(util/print-matrix height-map 6)

