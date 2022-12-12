(ns core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [util :as util]))

(def input
  (->> "day_10/input.txt"
       slurp
       str/split-lines
       (map #(str/split % #"\s"))
       (map (fn [[a b]] [a (edn/read-string b)]))))

;; Part 1
(def key-cycles [20 60 100 140 180 220])

(last
 (reduce
  (fn [[cycle reg prev-reg key-cycles strength] instr]
    (if (empty? key-cycles)
      [cycle reg prev-reg key-cycles strength]
      (let [next-key-cycle (first key-cycles)
            [new-key-cycles new-strength]
            (if (>= cycle next-key-cycle)
              [(rest key-cycles) (+ strength (* next-key-cycle prev-reg))]
              [key-cycles strength])]
        (cond
          (= (first instr) "noop") [(+ 1 cycle) reg reg new-key-cycles new-strength]
          :else [(+ 2 cycle) (+ reg (second instr)) reg new-key-cycles new-strength]))))
  [0 1 1 key-cycles 0]
  input))

;; Part 2
(def t
  ((reduce
    (fn [[instructions cycle-counter output reg prev-reg] cycle]
      (let [new-output (if (>= 1 (util/abs (- prev-reg cycle))) (str output "#") (str output "."))]
        (if (= cycle-counter 0)
          (let [instr (first instructions)]
            (if (= (first instr) "noop") [(rest instructions) 0 new-output reg reg]
                [(rest instructions) 1 new-output (+ reg (second instr)) reg]))
          [instructions (dec cycle-counter) new-output reg reg])))
    [input 0 "" 1 1]
    (flatten (repeat 6 (range 0 40)))) 2))

(dorun (map println
            (re-seq #".{40}" t)))




