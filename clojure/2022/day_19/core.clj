(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "2022/day_19/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map (fn [[name ore-ore clay-ore obs-ore obs-clay geo-ore geo-obs]]
              [name
               {:ore [ore-ore 0 0]
                :clay  [clay-ore 0 0]
                :obs  [obs-ore obs-clay 0]
                :geo [geo-ore 0 geo-obs]}]))
       (into (sorted-map))))

(defn get-options
  [{robots :robots} prices]
  (let [max-vals (apply map max (map second prices))]
    (reverse
     (cond-> []
       (> (nth max-vals 0) (robots 0) 0) (conj :ore)
       (and (> (nth max-vals 1) (robots 1)) (> (robots 0) 0)) (conj :clay)
       (and (> (nth max-vals 2) (robots 2)) (> (robots 0) 0) (> (robots 1) 0)) (conj :obs)
       (and (> (robots 0) 0) (> (robots 2) 0)) (conj :geo)))))

(def option-to-robot
  {:ore [1 0 0]
   :clay [0 1 0]
   :obs [0 0 1]
   :geo [0 0 0]})

(defn apply-option
  [{res :res robots :robots geo :geo minute :minute :as state} path option max-minutes prices]
  ;; (println state path option)
  (if (>= minute max-minutes) state
      (let [res-after-purchase (mapv - res (prices option))
            new-res-with-purchase (mapv + res-after-purchase robots) ; Updated resources
            new-res-without-purchase (mapv + res robots)
            new-robots (mapv + robots (option-to-robot option))
            new-geo (+ geo (if (= option :geo) (- max-minutes (inc minute)) 0))]
        (if (< (apply min res-after-purchase) 0)
          (apply-option {:res new-res-without-purchase
                         :robots robots
                         :geo geo
                         :minute (inc minute)}
                        path
                        option max-minutes prices)
          {:res new-res-with-purchase
           :robots new-robots
           :geo new-geo
           :minute (inc minute)}))))

(defn most-geos [{geo :geo minute :minute :as state} path max-minutes prices]
  (if (>= minute max-minutes) geo
      (loop [queue (get-options state prices)
             max-geos 0]
        (if (empty? queue) max-geos
            (let [next-option (first queue)
                  next-state (apply-option state path next-option max-minutes prices)
                  geos (most-geos next-state (conj path next-option) max-minutes prices)
                  new-max-geos (max max-geos geos)]
              (recur (rest queue) new-max-geos))))))

(def start
  {:res [0 0 0]
   :robots [1 0 0]
   :geo 0
   :minute 0})

;; Part 1

(def geos
  (doall (map (fn [[a b]] [a (time (most-geos start [] 24 b))]) input)))
geos

(def full-geos geos)
full-geos

(apply +
       (map (fn [[a b]] (* a b)) full-geos))


;; Part 2
(def geos-2
  (doall (map (fn [[a b]] [a (time (most-geos start [] 32 b))]) (take 3 input))))

geos-2
(apply * (map second geos-2))