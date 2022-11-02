(ns day_3
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.math.numeric-tower :as math]))

(def input_ (map #(str/split % #"")
                 (str/split-lines
                  (slurp "day_3/input.txt"))))

(def observations (count input_))
(def observation_length (count (first input_)))

(def input (map #(map edn/read-string %) input_))

(def t (apply map + input))

(def l (map #(math/round (/ % observations)) t))

(defn bin_to_int_ [head tail pos]
  (if (== pos 0)
    head
    (+ (math/expt (* head 2) pos)
       (bin_to_int_ (first tail) (rest tail) (- pos 1)))))

(defn bin_to_int [list]
  (bin_to_int_ (first list) (rest list) (- observation_length 1)))

(*
 (bin_to_int l)
 (bin_to_int (map #(+ 1 %) (map - l))))