(ns examples.math.core
  (:refer-clojure :exclude [vector]))

(defn matrix [m]
  (.matrix js/math (clj->js m)))

(def vector matrix)

(defn index [x n]
  (let [i (.index js/math n)]
    (.subset js/math x i)))

;(defn vector [v]
  ;(.transpose js/math (matrix v)))

(defn orthographic-matrix [left right top bottom near far]
  (let [a  (/  2 (- right left))
        b  (/  2 (- top bottom))
        c  (/ -2 (- far near))
        x1 (- (/ (+ right left) (- right left)))
        x2 (- (/ (+ top bottom) (- top bottom)))
        x3 (- (/ (+ far near)   (- far near)))]
    (matrix [[a 0 0 x1]
             [0 b 0 x2]
             [0 0 c x3]
             [0 0 0 1]])))
