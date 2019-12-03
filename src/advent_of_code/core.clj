(ns advent-of-code.core
  (:require
   [advent-of-code.day-01 :as day-01]
   [advent-of-code.day-03 :as day-03])
  (:gen-class))

(defmacro run-problem
  [n part]
  (let [ns (format "day-%02d" n)
        part-str (case part
                   :part-a "part-a"
                   :part-b "part-b")]
    (list
     (symbol ns part-str)
     (symbol ns "input"))))

(defmacro print-day
  [n]
  `(do
     (println ~(format "Day %02d" n))
     (println "Part A:" (run-problem ~n :part-a))
     (println "Part B:" (run-problem ~n :part-b))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-day 1)
  (print-day 3))
