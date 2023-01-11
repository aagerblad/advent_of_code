(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "2022/day_21/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"[a-z]{4}|\d+|[\+\/\-\*]" %))
       (map (fn [[a & b]] [a b]))
       (into {})))

(defn calc-val
  [list name]
  (let [val-to-be-eval (list name)]
    (if (= (count val-to-be-eval) 1)
      (edn/read-string (first val-to-be-eval))
      (let [[first-val operator second-val]  val-to-be-eval]
        ((eval (symbol operator))
         (calc-val list first-val)
         (calc-val list second-val))))))

;; Part 1
(time
 (calc-val input "root"))



;; Part 2
(def fir-start-point (nth (input "root") 0))
(def sec-start-point (nth (input "root") 2))

(def bin-input (assoc input "humn" ["-100000"]))

(def second-val (calc-val bin-input sec-start-point))

(loop [lower-bound 0
       upper-bound 10000000000000]
  (let [guess (long (/ (+ lower-bound upper-bound) 2))
        new-list (assoc input "humn" [(str guess)])
        cur-val (- (calc-val new-list fir-start-point) second-val)]
    (cond (= 0 cur-val) guess
          (> 0 cur-val) (recur lower-bound guess)
          (< 0 cur-val) (recur guess upper-bound))))