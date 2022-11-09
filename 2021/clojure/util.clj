(ns util)

(defn transpose [m]
  (apply mapv vector m))

(defn drop-index [col idx]
  (filter identity (map-indexed #(if (not= %1 idx) %2) col)))