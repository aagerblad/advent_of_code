(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [util :as util]))

(def input
  (->> "day_14/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"\d+" %))
       (map #(map edn/read-string %))
       (map #(partition 2 %))))

(def flattened (partition 2 (flatten input)))

;; (def l-width (apply min (map first flattened)))
(def l-width 0)

(def new-input
  (map
   #(map
     (fn [[a b]]
       (list (- a l-width -5) b)) %)
   input))


;; (def r-width (apply max (map first flattened)))
(def r-width 1000)
(def height (apply max (map second flattened)))


(def start-point [(- 500 l-width -5),0])


(def empty-map
  (-> r-width
      (+ 12)
      (- l-width)
      (repeat ".")
      vec
      (#(repeat (inc height) %))
      vec))

(util/print-matrix empty-map)

(defn gen-line [[sx sy] [ex ey]]
  (let [x-vals (sort [sx ex])
        y-vals (sort [sy ey])
        x-range (range (first x-vals) (inc (second x-vals)))
        y-range (range (first y-vals) (inc (second y-vals)))]
    (for [x x-range y y-range] [x y])))



(def starting-map
  (reduce
   (fn [m lines]
     (reduce
      (fn [m_ [start-point end-point]]
        (let [line (gen-line start-point end-point)]
          (reduce
           (fn [m__ dot]
             (assoc-in m__ [(dot 1) (dot 0)] "#"))
           m_
           line)))
      m
      (partition 2 1 lines)))
   empty-map
   new-input))

(util/print-matrix starting-map)


;; Part 1
(util/print-matrix
 (nth
  (loop [m starting-map
         sand start-point
         i 0]
    (cond
      (= (get-in m [(inc (sand 1))  (sand 0)]) ".") (recur m [(sand 0) (inc (sand 1))] i)
      (= (get-in m [(inc (sand 1)) (dec (sand 0))]) ".") (recur m [(dec (sand 0)) (inc (sand 1))] i)
      (= (get-in m [(inc (sand 1)) (inc (sand 0))]) ".") (recur m [(inc (sand 0)) (inc (sand 1))] i)
      :else
      (let [m_ (assoc-in m [(sand 1) (sand 0)] "o")]
        (if (= (mod i 50) 0)
          (do (println) (util/print-matrix m_))
          nil)
        (if (= (sand 1) height)
          (list m_ sand i)
          (recur m_ start-point (inc i))))))
  0))

;; Part 2
(def r (first empty-map))
(def starting-map-2 (conj starting-map r (map (fn [_] "#") r)))

(nth
 (loop [m starting-map-2
        sand start-point
        i 0]
   (cond
     (= (get-in m [(inc (sand 1))  (sand 0)]) ".") (recur m [(sand 0) (inc (sand 1))] i)
     (= (get-in m [(inc (sand 1)) (dec (sand 0))]) ".") (recur m [(dec (sand 0)) (inc (sand 1))] i)
     (= (get-in m [(inc (sand 1)) (inc (sand 0))]) ".") (recur m [(inc (sand 0)) (inc (sand 1))] i)
     :else
     (let [m_ (assoc-in m [(sand 1) (sand 0)] "o")]
      ;;  (if (= (mod i 50) 0)
      ;;    (do (println) (util/print-matrix m_))
      ;;    nil)
       (if (= (sand 1) 0)
         (list m_ sand i)
         (recur m_ start-point (inc i))))))
 2)