(ns core
  (:require [clojure.string :as str]
            [util :as util]))

(def winds
  (->> "day_17/input.txt"
       slurp
       str/trim
       char-array
       (#(flatten (for [x %] [x \d])))))

(def mat (util/create-xy-matrix \.
                                7 ;; Width
                                1000000 ;; Height
                                ))
(def rocks [\- \+ \J \I \O])

(defn get-rock [rock-type height]
  (mapv #(mapv + [0 height] %)
        (case rock-type
          \- [[2 0] [3 0] [4 0] [5 0]]
          \+ [[2 1] [3 1] [4 1] [3 2] [3 0]]
          \J [[2 0] [3 0] [4 0] [4 1] [4 2]]
          \I [[2 0] [2 1] [2 2] [2 3]]
          \O [[2 0] [2 1] [3 0] [3 1]])))

(defn draw-rock
  ([mat rock val] (reduce
                   (fn [mat pixel] (assoc-in mat pixel val))
                   mat
                   rock))
  ([mat rock] (draw-rock mat rock "#")))

(defn next-pattern
  [rocks last-rock]
  (nth rocks (mod (inc (.indexOf rocks last-rock)) (count rocks))))

(defn collision?
  [mat rock]
  (some #(= "#" (get-in mat %)) rock))

(defn below-bottom?
  [rock]
  (<= (second (apply map min rock)) -1))

(defn move-rock
  [rock movement]
  (let [new-rock (mapv #(mapv + movement %) rock)]
    (cond
      (<= (first (apply map min new-rock)) -1) rock
      (>= (first (apply map max new-rock)) 7) rock
      :else new-rock)))

(def blow-dir {\> [1 0] \< [-1 0] \d [0 -1]})

(defn get-height
  [rock]
  (second (apply map max rock)))

(defn run-game [rocks-to-drop]
  (loop [mat mat
         move-queue winds
         cur-pattern \-
         cur-rock (get-rock \- 3)
         rocks-fallen 0
         height 0]
    (cond
      (>= rocks-fallen rocks-to-drop) (inc height)
      (empty? move-queue) (recur mat
                                 winds
                                 cur-pattern
                                 cur-rock
                                 rocks-fallen
                                 height)
      :else
      (let [movement (first move-queue)
            new-rock (move-rock cur-rock (blow-dir movement))
            next-pattern (next-pattern rocks cur-pattern)
            new-height (max height (get-height cur-rock))]
        (cond
          (or (and (= movement \d) (collision? mat new-rock))
              (below-bottom? new-rock))
          (recur (draw-rock mat cur-rock)
                 (rest move-queue)
                 next-pattern
                 (get-rock next-pattern (+ new-height 4))
                 (inc rocks-fallen)
                 new-height)

          (collision? mat new-rock)
          (recur mat
                 (rest move-queue)
                 cur-pattern
                 cur-rock
                 rocks-fallen
                 height)

          :else
          (recur mat
                 (rest move-queue)
                 cur-pattern
                 new-rock
                 rocks-fallen
                 height))))))

;; Part 1
(run-game 2022)

;; Part 2
(defn cycle-start?
  [cur-pattern start-pattern move-queue winds]
  (and (= cur-pattern start-pattern) (= (count move-queue) (count winds))))

;; Find cycle
(loop [mat mat
       move-queue winds
       cur-pattern \-
       cur-rock (get-rock \- 3)
       rocks-fallen 0
       prev-rocks-fallen 0
       height 0
       prev-height 0]
  (when (cycle-start? cur-pattern \J move-queue winds)
    (println (- rocks-fallen prev-rocks-fallen) (- height prev-height)))

  (cond
    (>= rocks-fallen 202200) (inc height)
    (empty? move-queue) (recur mat
                               winds
                               cur-pattern
                               cur-rock
                               rocks-fallen
                               prev-rocks-fallen
                               height
                               prev-height)
    :else
    (let [movement (first move-queue)
          new-rock (move-rock cur-rock (blow-dir movement))
          next-pattern (next-pattern rocks cur-pattern)
          new-height (max height (get-height cur-rock))
          new-prev-rocks-fallen (if (cycle-start? cur-pattern \J move-queue winds) rocks-fallen prev-rocks-fallen)
          new-prev-height (if (cycle-start? cur-pattern \J move-queue winds) height prev-height)]
      (cond
        (or (and (= movement \d) (collision? mat new-rock))
            (below-bottom? new-rock))
        (recur (draw-rock mat cur-rock)
               (rest move-queue)
               next-pattern
               (get-rock next-pattern (+ new-height 4))
               (inc rocks-fallen)
               new-prev-rocks-fallen
               new-height
               new-prev-height)

        (collision? mat new-rock)
        (recur mat
               (rest move-queue)
               cur-pattern
               cur-rock
               rocks-fallen
               new-prev-rocks-fallen
               height
               new-prev-height)

        :else
        (recur mat
               (rest move-queue)
               cur-pattern
               new-rock
               rocks-fallen
               new-prev-rocks-fallen
               height
               new-prev-height)))))
;; 1745 2752

;; Find point where cycle starts
(mod 1000000000000 1745)
(+ 1745 1010)
;; 2755

;; Find height where cycle starts
(run-game 2755)
;; 4363

;; Cycles until goal
(int (/ (- 1000000000000 1745) 1745))

;; Height at cycle start
;; + (cycles * cycle-height)
(+ 4363 (* 573065901 2752))