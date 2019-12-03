(ns advent-of-code.day-03
  (:require
   [clojure.java.io :as io]
   [clojure.set :as s]
   [clojure.string :as string]))

(defn- parse-instruction
  [s]
  (let [[_ d n] (re-matches #"(R|L|D|U)(\d+)" s)
        dir (case d
              "R" :right
              "L" :left
              "U" :up
              "D" :down)]
    [dir (Integer/parseInt n)]))

(def input
  (let [txt (-> "input/day_03.txt" io/resource slurp string/split-lines)]
    (map #(->> (string/split % #",")
               (map parse-instruction)
               (mapcat (fn [[d n]] (repeat n d))))
         txt)))

(defn- delta-from-dir
  [dir]
  (case dir
    :right #(update % 1 inc)
    :left #(update % 1 dec)
    :down #(update % 0 inc)
    :up #(update % 0 dec)))

(defn- handle-step
  [{:keys [current-head accum]} d]
  {:current-head ((delta-from-dir d) current-head)
   :accum (conj accum current-head)})

(defn- visited-spaces
  [directions]
  (let [res (reduce handle-step {:accum '() :current-head [0 0]} directions)]
    (conj (:accum res) (:current-head res))))

(defn- manhatten-distance
  ([pos] (manhatten-distance pos [0 0]))
  ([[x1 y1] [x2 y2]]
   (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))))

(defn- collision-points
  [first-input second-input]
  (let [first_places (into #{} (visited-spaces first-input))]
    (:matched-places
     (reduce
      (fn [{:keys [current-place] :as accum} dir]
        (let [new-place ((delta-from-dir dir) current-place)]
          (cond-> accum
            true
            (assoc :current-place new-place)
            (first_places new-place)
            (update :matched-places #(conj % new-place)))))
      {:matched-places #{} :current-place [0 0]}
      second-input))))

(defn part-a
  [input]
  (->> (collision-points (first input) (second input))
       (sort-by manhatten-distance)
       first
       manhatten-distance))

(defn- make-distance-map
  [input]
  (->> input
       visited-spaces
       reverse
       (map-indexed (fn [i v] {v i}))
       (into {})))

(defn part-b
  [input]
  (let [collisions (collision-points (first input) (second input))
        first-dist (make-distance-map (first input))
        second-dist (make-distance-map (second input))]
    (->> collisions
         (map #(+ (get first-dist %) (get second-dist %)))
         sort
         first)))
