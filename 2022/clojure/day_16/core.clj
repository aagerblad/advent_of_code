(ns core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> "day_16/input.txt"
       slurp
       str/split-lines
       (map #(re-seq #"([A-Z]{2}).+=(\d+).+valves?\s(.*)" %))
       (map #(rest (first %)))
       (map (fn [[name flow paths]]
              [name
               {:flow (edn/read-string flow)
                :paths (re-seq #"[A-Z]{2}" paths)}]))
       (into (sorted-map))))

input

(def nodes-with-flow (mapv first (filter (fn [x] (> (:flow (second x)) 0)) input)))

(defn visited?  [v coll] (some #(= % v) coll))

(defn find-neighbors [v coll] (:paths (get coll v)))

(defn graph-bfs [graph v]
  (loop [queue  [[v 0]]
         visited [v]
         distances []]
    (if (empty? queue) (assoc-in graph
                                 [v :distances]
                                 (into {}
                                       (filter
                                        #(visited? (first %) nodes-with-flow)
                                        distances)))                               ;; Base case - return visited nodes if the queue is empty
        (let [[v dist]    (first queue)
              neighbors   (find-neighbors v graph)
              not-visited (remove #(visited? % visited) neighbors)
              to-visit    (for [x not-visited] [x (inc dist)])
              new-queue   (vec (concat (rest queue) to-visit))]
          (if (visited? v visited)
            (recur new-queue visited distances)
            (recur new-queue
                   (conj visited v)
                   (conj distances [v dist])))))))

(def graph-dist (reduce graph-bfs input (map first input)))
graph-dist

(def graphs-with-flow (into {} (filter #(visited? (key %) nodes-with-flow) graph-dist)))

(def inner-graph (conj graphs-with-flow (first graph-dist)))
inner-graph

(defn dist-to-node
  [graph start end]
  (get-in graph [start :distances end]))

;; Part 1
(def best-score
  (memoize
   (fn [graph remaining-valves position time-left]
     (loop [queue remaining-valves
            max-score 0]
       (if (empty? queue) max-score
           (let [new-node (first queue)
                 dist (dist-to-node graph position new-node)
                 pressure (get-in graph [new-node :flow])
                 new-time-left (- time-left dist 1)
                 steam (+ (* pressure new-time-left)
                          (best-score graph
                                      (remove #(= % new-node) remaining-valves)
                                      new-node
                                      new-time-left))
                 new-max-score (max steam max-score)]
             (recur (rest queue) new-max-score)))))))

(best-score inner-graph (remove #(= % "AA") (keys inner-graph)) "AA" 30)

