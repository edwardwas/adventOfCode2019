(ns advent-of-code.core
  (:require
   [advent-of-code.day-01 :as day-01])
  (:gen-class))

(defmacro run-problem
  [n part]
  (let [ns (format "day-%02d" n)]
    (list
     (symbol ns (case part
                  :part-a "part-a"
                  :part-b "part-b"))
     (symbol ns "input"))))

(defn- print-day
  [n]
  (println (format "Day %02d" n))
  (println "Part A:" (run-problem 1 :part-a))
  (println "Part B:" (run-problem 1 :part-b)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (doseq [n (range 1 2)]
    (print-day n)))
