(ns advent-of-code.day-01
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input
  (mapv #(Integer. %)
   (-> "input/day_02.txt"
      io/resource
      slurp
      (string/trim)
      (string/split #","))))

(defn- read_memory_offset
  ([s] (read_memory_offset s 0))
  ([{:keys [address memory]} n]
   (get memory (+ address n))))

(defn- read_memory
  ([{:keys [memory]} n]
   (get memory n)))

(defn- apply-numeric-op-code
  [s f] 
  (println)
  (assoc-in
   s
   [:memory (read_memory_offset s 3)] 
   (f
    (read_memory s (read_memory_offset s 1))
    (read_memory s (read_memory_offset s 2)))))

(defmulti update-with-op-code (fn [s] (read_memory_offset s)))
(defmethod update-with-op-code 1
  [s] (apply-numeric-op-code s +))
(defmethod update-with-op-code 2
  [s] (apply-numeric-op-code s *))

(defn- run-computer
  [state]
  (if (= 99 (read_memory_offset state))
    state
    (-> state
        update-with-op-code
        (update :address #(+ 4 %))
        recur)))

(defn part-a
  [input]
  (get-in
   (run-computer
    {:memory (-> input (assoc 1 12) (assoc 2 2))
     :address 0})
   [:memory 0]))
