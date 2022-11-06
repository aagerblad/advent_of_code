(ns day_4.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input_
  (str/split
   (slurp "day_4/input.txt") #"\n\n"))

input_

(def draws (str/split (first input_) #","))
draws

(defn create_board [board_lines]
  (map #(str/split (str/trim %) #"\s+") (str/split-lines board_lines)))


(def boards
  (map create_board (rest input_)))

boards




(defn mark_row [row val]
  (map #(if (= val %) "x" %) row))

(defn mark_board [board val]
  (map #(mark_row % val) board))

(defn mark_boards [boards val]
  (map #(mark_board % val) boards))

(defn row_complete? [row]
  (= 5 (count (filter #(= "x" %) row))))

(defn transpose [m]
  (apply mapv vector m))

(defn board_complete? [board]
  (or
   (boolean
    (some true?
          (map row_complete? board)))
   (boolean
    (some true?
          (map row_complete? (transpose board))))))

(defn test_complete [boards]
  (map board_complete? boards))

(defn draw_numbers [boards draws]
  (if (empty? draws)
    [boards "" nil]
    (let [draw (first draws)
          new_boards (mark_boards boards draw)
          completed_boards (test_complete new_boards)]
      (if (some true? completed_boards)
        [new_boards draw (.indexOf completed_boards true)]
        (draw_numbers new_boards (rest draws))))))

(let [results (draw_numbers boards draws)]
  (*
   (edn/read-string (second results))
   (apply + (map edn/read-string (filter #(not= "x" %) (flatten (nth (first results) (last results))))))))