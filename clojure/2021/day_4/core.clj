(ns day_4.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [util :as util]))

(def input_
  (str/split
   (slurp "day_4/input.txt") #"\n\n"))

(def draws (str/split (first input_) #","))

(defn create_board [board_lines]
  (map
   #(str/split (str/trim %) #"\s+")
   (str/split-lines board_lines)))

(def boards
  (map create_board (rest input_)))

(defn print_boards [boards]
  (dorun (map #(print % "\n") (util/transpose boards))))

(defn mark_board [board val]
  (map #(replace {val "x"} %) board))

(defn mark_boards [boards val]
  (map #(mark_board % val) boards))

(defn row_complete? [row]
  (= 5 (count (filter #(= "x" %) row))))

(defn board_complete? [board]
  (or
   (boolean
    (some true?
          (map row_complete? board)))
   (boolean
    (some true?
          (map row_complete? (util/transpose board))))))

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


(print_boards boards)

(let [results (draw_numbers boards draws)]
  (*
   (edn/read-string (second results))
   (apply
    +
    (map edn/read-string
         (filter
          #(not= "x" %)
          (flatten
           (nth (first results) (last results))))))))

(defn drop_boards [boards completed_boards]
  (let [idx (.indexOf completed_boards true)]
    (println "completed_boards: " completed_boards)
    (println "index: " idx)
    (println ">= idx 0 " (>= idx 0))
    (if (>= idx 0)
      (let [remaining_boards (util/drop-nth idx boards)
            remaining_completed (util/drop-nth idx completed_boards)]
        (println remaining_completed)
        (if (some true? remaining_completed)
          (drop_boards remaining_boards remaining_completed)
          remaining_boards))
      boards)))

(defn draw_numbers_last_board [boards draws]
  ;; (if (> 3 (count boards))
  ;;   (println (first draws) boards)
  ;;   (println (first draws) (count boards)))
  (println " ")
  (println (first draws))
  (if (empty? draws)
    [boards ""]
    (let [draw (first draws)
          new_boards (mark_boards boards draw)
          completed_boards (test_complete new_boards)]
      (dorun (print_boards new_boards))
      (println "completed boards: " completed_boards)
      (println (some true? completed_boards))
      (if (some true? completed_boards)
        (if (= 1 (count new_boards))
          [new_boards draw]
          (draw_numbers_last_board
           (drop_boards new_boards completed_boards)
           (rest draws)))
        (draw_numbers_last_board new_boards (rest draws))))))

(draw_numbers_last_board boards draws)

(let [results (draw_numbers_last_board boards draws)]
  (println (second results))
  (*
   (edn/read-string (second results))
   (apply
    +
    (map edn/read-string
         (filter
          #(not= "x" %)
          (flatten
           (first (first results))))))))



