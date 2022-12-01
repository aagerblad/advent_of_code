(ns day_2.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (str/split-lines
            (slurp "day_2/input.txt")))

(def input_ (map #(str/split % #" ") input))

;; part 1
(defn parse [test]
  (case (first test)
    "forward" [(edn/read-string (last test)) 0]
    "down" [0 (edn/read-string (last test))]
    "up" [0 (- (edn/read-string (last test)))]))

(apply * (apply map + (map parse input_)))

;; part 2
(defn read_val [line]
  (edn/read-string (last line)))

(defn step [line lines dir depth dist]
  (if (not-empty lines)
    (case (first line)
      "forward" (step (second lines) (drop 1 lines) dir (+ depth (* dir (read_val line))) (+ dist (read_val line)))
      "down" (step (second lines) (drop 1 lines) (+ dir (read_val line)) depth dist)
      "up" (step (second lines) (drop 1 lines) (- dir (read_val line)) depth dist))
    (* depth dist)))

(step (first input_) input_ 0 0 0)


;; Version 2
(def input
  (->> "day_2/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"[a-z]+|\d+" %))
       (map (fn [[dir dist]] {:dir dir :dist (edn/read-string dist)}))
       vec))

((fn [{x :x y :y}] (* x y))
 (reduce (fn [pos step]
           (case (:dir step)
             "forward" (assoc pos :x (+ (pos :x) (step :dist)))
             "down" (assoc pos :y (+ (pos :y) (step :dist)))
             "up" (assoc pos :y (- (pos :y) (step :dist)))))
         {:x 0 :y 0}
         input))


((fn [{x :x y :y}] (* x y))
 (reduce (fn [pos step]
           (println pos step)
           (case (:dir step)
             "forward" (assoc pos :x (+ (pos :x) (step :dist)) :y (+ (pos :y) (* (pos :dir) (step :dist))))
             "down" (assoc pos :dir (+ (pos :dir) (step :dist)))
             "up" (assoc pos :dir (- (pos :dir) (step :dist)))))
         {:x 0 :y 0 :dir 0}
         input))
