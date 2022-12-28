(ns day_3.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.math.numeric-tower :as math]))

(defn inv [int]
  (+ (- int) 1))

(defn most_common_bit
  ([i up_or_down]
   (if up_or_down
     (map #(math/round (/ % (count i))) (apply map + i))
     (map inv (map #(math/round (/ % (count i))) (apply map + i)))))
  ([i]
   (most_common_bit i true)))

(defn bin_to_int_ [head tail pos]
  (if (== pos 0)
    head
    (+ (math/expt (* head 2) pos)
       (bin_to_int_ (first tail) (rest tail) (- pos 1)))))


(defn bin_to_int [list]
  (bin_to_int_ (first list) (rest list) (- (count list) 1)))

(defn filter_observations [input val]
  (filter
   #(= val (first %1))
   input))

(defn calculate_bit_pattern [input up_or_down]
  (if (== 1 (count input))
    (if (empty? (first input)) nil (first input))
    (let [cur_bit (first (most_common_bit input up_or_down))]
      (concat [cur_bit]
              (calculate_bit_pattern
               (map rest (filter_observations input cur_bit)) up_or_down)))))

;; Input
(def input_ (map #(str/split % #"")
                 (str/split-lines
                  (slurp "day_3/input.txt"))))

(def input (map #(map edn/read-string %) input_))


;; Part 1
(def l
  (most_common_bit input))

(println "Value: "
         (*
          (bin_to_int l)
          (bin_to_int (map #(+ 1 %) (map - l)))))


;; Part 2
(*
 (bin_to_int (calculate_bit_pattern input true))
 (bin_to_int (calculate_bit_pattern input false)))
