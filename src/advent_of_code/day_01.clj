(ns advent-of-code.day-01
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input
  (->> "input/day_01.txt"
     io/resource
     slurp
     string/split-lines
     (mapv #(Integer. %)))) 

(defn- mass->fuel
  [mass]
  (- (Math/floor (/ mass 3)) 2))

(defn part-a
  [x]
  (->> x
       (map mass->fuel)
       (reduce +)))

(defn- mass->extra-fuel
  [mass]
  (->> mass
       (iterate mass->fuel)
       (drop 1)
       (take-while #(> % 0))
       (reduce +)))

(defn part-b
  [x]
  (->> x
       (map mass->extra-fuel)
       (reduce +)))
