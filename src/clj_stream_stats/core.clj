(ns clj-stream-stats.core
  (:use [clojure.math.numeric-tower :only (sqrt)]))

(defn square
  [n]
  (* n n))

(defn differences
  [x xs]
  (map #(- % x) xs))

(defn squares
  [xs]
  (map square xs))

(defn sum
  [xs]
  (reduce + xs))

(defn mean
  "Calculates a non-streaming mean from a sequence of numbers."
  [& xs]
  (/ (sum xs) (count xs)))

(defn variance
  "Calculates a non-streaming variance for a set of samples."
  [mean xs]
  (let [n (count xs)]
    (/ (sum (squares (differences mean xs)))
       n)))

(defn standard-deviation
  "Non-streaming standard-deviation for a sequence of numbers."
  [mean xs]
  (sqrt (variance mean xs)))

(defn accum-mean
  "An accumulating, streaming mean calculator.

   Returns a function that takes a variable number of arguments
   representing the values to compute
   the mean for:

   (def calc-mean (accum-mean))
   (calc-mean 1 2 3 4 5) => 3"
  []
  (let [n (atom 0)
        v (atom 0)]
    (fn [& xs]
      (/ (swap! v + (sum xs))
         (swap! n + (count xs))))))

(defn accum-variance
  "Returns an accumulating function, suitable for calculating the streaming
   variance of a sequence of numbers.
   Returned function expects mean and current observation:
   (def a (accum-variance))
   (a 1 [1]) => ..."
  []
  (let [n (atom 0)
        sum-squares-of-difference (atom 0)]
    (fn [mean xs]
      (/ (swap! sum-squares-of-difference + (sum (squares (differences mean xs))))
         (swap! n + (count xs))))))
