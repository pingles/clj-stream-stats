(ns clj-stream-stats.test.core
  (:use [clojure.test]
        [clj-stream-stats.core] :reload))

(deftest non-streaming-mean
  (is (= 10
         (mean 10)))
  (is (= 3
         (mean 5 4 3 2 1))))

(deftest streaming-mean
  (let [a (accum-mean)]
    (is (= 1
           (a 1)))
    (is (= 3/2
           (a 2)))
    (is (= 2
           (a 3)))
    (is (= 3
           (a 4 5)))))

(deftest non-streaming-variance
  (is (= 2
         (variance 3 [1 2 3 4 5]))))

(deftest non-streaming-standard-deviation
  (is (= 1.4142135623730951
         (standard-deviation 3 [1 2 3 4 5]))))

;; Streaming variance is approximate since the mean over
;; all observations will be varying also.
(deftest streaming-variance
  (let [a (accum-variance)]
    (a (mean 1 2 3 4) [1 2 3 4])
    (is (= 9/5
           (a 3 [5])))))
