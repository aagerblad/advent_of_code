(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [util :as util]))

(defn parse-stacks [stacks]
  (->> stacks
       str/split-lines
       (map char-array)
       (map #(partition 4 4 [""] %))
       (map #(map second %))
       drop-last
       util/transpose
       (map #(filter (fn [x] (not= x \space)) %))
       (map vec)
       (concat [[]])
       vec))

(defn parse-moves [moves]
  (->> moves
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map (fn [[move from to]] {:move move :from from :to to}))))

(def input
  (->> "day_5/input.txt"
       slurp
       (#(str/split % #"\n\n"))
       ((fn [[stacks moves]]
          {:stacks (parse-stacks stacks)
           :moves (parse-moves moves)}))))


;; Part 1
(defn make-move [stacks move]
  (let [from-stack (split-at (:move move) (stacks (:from move)))
        to-stack (concat (reverse (first from-stack)) (stacks (:to move)))]
    (assoc stacks (:from move) (second from-stack) (:to move) to-stack)))


(println
 (apply str (rest (map first (reduce make-move (:stacks input) (:moves input))))))

;; Part 2
(defn make-move-pt-2 [stacks move]
  (let [from-stack (split-at (:move move) (stacks (:from move)))
        to-stack (concat (first from-stack) (stacks (:to move)))]
    (assoc stacks (:from move) (second from-stack) (:to move) to-stack)))


(println
 (apply str (rest (map first (reduce make-move-pt-2 (:stacks input) (:moves input))))))