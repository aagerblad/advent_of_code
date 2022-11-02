(ns day_2
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
