(ns util)

(defn transpose [m]
  (apply mapv vector m))

(defn drop-nth [n coll]
  (keep-indexed #(when (not= %1 n) %2) coll))


(defn group-n [n arr]
  (if (>= n (count arr))
    [arr]
    (concat [(take n arr)] (group-n n (nthrest arr n)))))

(defn print-matrix
  ([board width] (doseq [row board]
                   (doseq [val row]
                     (print (str  (apply str (repeat (- width (count (str val))) " ")) val)))
                   (println)))
  ([board] (print-matrix board 0)))

(defn shifted-mod [x m s]
  (+ s (mod (- x s) m)))

(defn abs [n] (max n (- n)))

(defn create-xy-matrix
  ([val width height w-pad h-pad] (-> height
                                      (+ h-pad)
                                      (repeat val)
                                      vec
                                      (#(repeat (+ width w-pad) %))
                                      vec))
  ([val width height] (create-xy-matrix val width height 0 0)))

(defn print-xy-matrix
  ([board width] (print-matrix (transpose board) width))
  ([board] (print-matrix (transpose board))))