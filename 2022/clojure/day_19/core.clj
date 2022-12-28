(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_19/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map (fn [[name ore-ore clay-ore obs-ore obs-clay geo-ore geo-obs]]
              [name
               {:ore [ore-ore 0 0 0]
                :clay  [clay-ore 0 0]
                :obs  [obs-ore obs-clay 0]
                :geo [geo-ore 0 geo-obs]}]))
       (into (sorted-map))))

(input 4)



(def start
  {:res [0 0 0]
   :geos 0
   :robots [1 0 0 0]})


(map - [10 10 10] [1 2 3])

(defn enough-res?
  [res price]
  (>= (apply min (map - res price)) 0))

(defn get-options
  [state prices]
  (cond-> [[0 0 0 0]]
    (enough-res? (state :res) (prices :ore)) (conj [1 0 0 0])
    (enough-res? (state :res) (prices :clay)) (conj [0 1 0 0])
    (enough-res? (state :res) (prices :obs)) (conj [0 0 1 0])
    (enough-res? (state :res) (prices :geo)) (conj [0 0 0 1])))


(defn find-ind
  [option]
  (.indexOf option 1))

(defn step
  [state prices step]
  (let [options (get-options state prices)]
    (map (fn [option])
         options)))