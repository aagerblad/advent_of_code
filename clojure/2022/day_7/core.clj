(ns core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.walk :as walk]))


(defn build-folder-structure [[cur-dir & stack] [a & b]]
  (if (= a "$")
    (if (= (first b) "ls")
      ;; Do ls
      (concat [cur-dir] stack)

      ;; Do cd
      (if (= (second b) "..")
        ;; Do cd ..
        (let [new-cur-dir (first stack)
              new-con (concat [cur-dir] (new-cur-dir :con))]
          (concat [(assoc new-cur-dir :con new-con)] (rest stack)))

        ;; Do cd x
        (concat [{:n (second b) :type :dir :con [] :size 0}] [cur-dir] stack)))


    (if (= a "dir")
      (concat [cur-dir] stack)

      (let [new-con (concat [{:n (first b) :size (edn/read-string a) :type :file}]
                            (cur-dir :con))
            new-size (+ (:size cur-dir) (edn/read-string a))]
        (concat [(assoc cur-dir :con new-con)] stack)))))

(defn print-folder
  ([folders pad]
   (if (= :dir (:type folders))
     (println (str pad "- " (:n folders) "/ " (:size folders)))
     (println (str pad "- " (:n folders) " " (:size folders))))
   (dorun (map #(print-folder % (str "  " pad)) (:con folders)))
   nil)
  ([folders] (print-folder folders "")))

(def input
  (->> "day_7/input.txt"
       slurp
       str/split-lines
       (map #(str/split % #"\s"))))

;; Part 1
(def folder-structure (first
                       (reduce
                        build-folder-structure
                        [{}]
                        input)))

(def folder-structure-with-folder-sizes
  (walk/postwalk
   (fn [x]
     (cond
       (= (:type x) :dir)
       (let [file-sizes (apply + (map :size (:con x)))]
         (assoc x :size file-sizes))
       :else x)) folder-structure))

(apply + (map :size
              (filter #(and (= (:type %) :dir) (<= (:size %) 100000))
                      (tree-seq identity :con folder-structure-with-folder-sizes))))

;; Part 2
(def needed-space
  (- 30000000
     (- 70000000
        (:size folder-structure-with-folder-sizes))))

(apply min
       (map :size
            (filter #(and (= (:type %) :dir) (>= (:size %) needed-space))
                    (tree-seq identity :con folder-structure-with-folder-sizes))))