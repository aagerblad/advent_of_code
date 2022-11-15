(ns util)

(defn transpose [m]
  (apply mapv vector m))

(defn drop-nth [n coll]
  (keep-indexed #(if (not= %1 n) %2) coll))