(ns core
  (:require [clojure.string :as str]))

(defn join-lists [[a b]]
  (let [la (count a)
        lb (count b)
        pada (repeat (max 0 (- lb la)) nil)
        padb (repeat (max 0 (- la lb)) nil)]
    (map (fn [av bv] [av bv]) (into a pada) (into b padb))))

(defn compare-vals
  ([x]
   (let [joined-lists (join-lists x)]
     (reduce
      (fn [val [ah bh]]
        (cond
          (true? val) (reduced true)
          (false? val) (reduced false) ;; 
          (nil? ah) (reduced true)
          (nil? bh) (reduced false)
          (and (int? ah) (int? bh)) (cond (< ah bh) (reduced true)
                                          (> ah bh) (reduced false)
                                          :else nil)
          (and (coll? ah) (coll? bh)) (compare-vals [ah bh])
          (int? ah) (compare-vals [[ah] bh])
          (int? bh) (compare-vals [ah [bh]])
          :else nil))
      nil
      joined-lists)))
  ([a b] (compare-vals [a b])))


(def input
  (->> "day_13/test_input.txt"
       slurp
       str/split-lines
       (map load-string)
       (partition 2 3 nil)))

(compare-vals
 (second (first input))
 (first (first input)))


(->> (map compare-vals input)
     (map (fn [a b] [a b]) (range 1 (inc (count input))))
     (filter (fn [[_ a]] a))
     (map first)
     (apply +))


(def input-2
  (->> "day_13/input.txt"
       slurp
       str/split-lines
       (map load-string)
       (remove nil?)
       (#(into % [[[2]] [[6]]]))))


(apply * (map #(inc (.indexOf (sort compare-vals input-2) %)) [[[2]] [[6]]]))