(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "2022/day_20/input.txt"
       slurp
       str/split-lines
       (map edn/read-string)))

(nth (cycle input) 1000)

(defn index-of-key
  [list key]
  (.indexOf (map first list) key))

(defn index-of-val
  [list key]
  (.indexOf (map second list) key))

(defn list-insert [lst elem index]
  (let [[l r] (split-at index lst)]
    (concat l [elem] r)))

(def list
  (map-indexed (fn [idx itm] [idx itm]) input))

(def nums (count list))
nums

(defn mix-list [list]
  (let [nums (count list)]
    (loop [i 0
           l list]
      (if (= (mod i 1000) 0)
        (println i (count l)))
      (if (>= i nums) l
          (let [cur-index (index-of-key l i)
                [ord val] (nth l cur-index)
                [pre post] (split-at cur-index l)
                new-index (inc (mod (+ cur-index val -1) (- nums 1)))
            ;; _ (println val cur-index (+ cur-index val) new-index)
                new-l (list-insert (concat pre (rest post)) [ord val] new-index)]
            (recur (inc i) new-l))))))

;; Part 1
(def a (mix-list list))
(apply +
       (map #(second (nth (cycle a) (+ (index-of-val a 0) %)))
            [1000 2000 3000]))

;; Part 2
(def new-list (map (fn [[i val]] [i (* val 811589153)]) list))

(def b (mix-list new-list))

(def c
  (loop [i 0
         l new-list]
    (if (>= i 10) l
        (recur (inc i) (mix-list l)))))
(apply +
       (map #(second (nth (cycle c) (+ (index-of-val c 0) %)))
            [1000 2000 3000]))