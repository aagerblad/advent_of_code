(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "2022/day_22/input.txt"
       slurp
       str/split-lines))