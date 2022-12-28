(ns core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(def input
  (->> "day_11/input.txt"
       slurp
       str/split-lines
       (partition 7 7 "")
       (mapv (fn [[name items operation test if-true if-false]]
               {:name  (edn/read-string (re-find #"\d" name))
                :items (mapv edn/read-string (re-seq #"\d+" items))
                :operation (str/split (re-find #"[+*].+" operation) #"\s")
                :test (edn/read-string (re-find #"\d+" test))
                :pass {true (edn/read-string (re-find #"\d" if-true))
                       false (edn/read-string (re-find #"\d" if-false))}
                :inspected 0}))))

(def nr-monkey (count input))
(def ops {"*" * "+" +})

(defn operation [[op amount] val]
  (if (= amount "old") ((ops op) val val)
      ((ops op) (edn/read-string amount) val)))


(def mod-prop
  (apply * (map :test input)))

;; Part 1
(defn run-one-monkey [monkeys_ monkey-nr]
  (let [monkey (monkeys_ monkey-nr)]
    (first
     (reduce (fn [[monkeys from-monkey] item]
               (let [new-item (int (/ (operation (:operation from-monkey) (mod item mod-prop)) 3)) ;; Create new item
                     test (= 0 (mod new-item (:test from-monkey))) ;; Test where item get's thrown
                     to-monkey (monkeys ((:pass from-monkey) test)) ;; 
                     updated-to-monkey (update to-monkey :items #(vec (conj % new-item)))
                     updated-from-monkey (update from-monkey :items #(vec (rest %)))
                     updated-from-monkey (update updated-from-monkey :inspected #(inc %))]
                 [(assoc monkeys (:name from-monkey) updated-from-monkey (:name to-monkey) updated-to-monkey) updated-from-monkey]))
             [monkeys_ monkey]
             (:items monkey)))))

(def rounds (range 0 20))
(apply *
       (take 2
             (sort >
                   (map :inspected
                        (reduce
                         (fn [monkeys__ r]
                           (loop [i monkeys__
                                  monkey-nr 0]
                             (if (= nr-monkey monkey-nr)
                               i
                               (recur (run-one-monkey i monkey-nr) (inc monkey-nr)))))
                         input
                         rounds)))))



;; Part 1
(defn run-one-monkey-2 [monkeys_ monkey-nr]
  (let [monkey (monkeys_ monkey-nr)]
    (first
     (reduce (fn [[monkeys from-monkey] item]
               (let [new-item (operation (:operation from-monkey) (mod item mod-prop)) ;; Create new item
                     test (= 0 (mod new-item (:test from-monkey))) ;; Test where item get's thrown
                     to-monkey (monkeys ((:pass from-monkey) test)) ;; 
                     updated-to-monkey (update to-monkey :items #(vec (conj % new-item)))
                     updated-from-monkey (update from-monkey :items #(vec (rest %)))
                     updated-from-monkey (update updated-from-monkey :inspected #(inc %))]
                 [(assoc monkeys (:name from-monkey) updated-from-monkey (:name to-monkey) updated-to-monkey) updated-from-monkey]))
             [monkeys_ monkey]
             (:items monkey)))))

(def rounds-2 (range 0 10000))
(apply *
       (take 2
             (sort >
                   (map :inspected
                        (reduce
                         (fn [monkeys__ r]
                           (loop [i monkeys__
                                  monkey-nr 0]
                             (if (= nr-monkey monkey-nr)
                               i
                               (recur (run-one-monkey-2 i monkey-nr) (inc monkey-nr)))))
                         input
                         rounds-2)))))


