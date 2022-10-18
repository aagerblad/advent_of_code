(ns day_1
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(def input (map
            edn/read-string
            (str/split-lines
             (slurp "day_1/input.txt"))))

(defn testfn [x y]
  (> x y))

(count
 (filter identity (map testfn
                       (drop 1 input)
                       (drop-last 1 input))))

