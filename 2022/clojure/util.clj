(ns util)

(defn transpose [m]
  (apply mapv vector m))

(defn drop-nth [n coll]
  (keep-indexed #(when (not= %1 n) %2) coll))


(defn group-n [n arr]
  (if (>= n (count arr))
    [arr]
    (concat [(take n arr)] (group-n n (nthrest arr n)))))

(defn print-matrix [board]
  (doseq [row board]
    (doseq [val row] (print val))
    (println)))

(defn shifted-mod [x m s]
  (+ s (mod (- x s) m)))