(ns examples.math.core
  (:refer-clojure :exclude [vector]))

(defn matrix []
  (.create js/mat4))

(defn vector [x y z w]
  (.fromValues js/vec4 x y z w))

(defn orthographic-matrix [left right top bottom near far]
  (let [m (.create js/mat4)]
    (.ortho js/mat4 m left right bottom top near far)
    m))

(defn view-matrix [width height]
  (let [aspect-ratio 1 ;;(/ width height) ;;TODO delete
        view-size 10 
        x (/ (* aspect-ratio view-size) 2)
        y (/ view-size 2)
        z 1000]
    (orthographic-matrix (- x) x y (- y) (- z) z)))

(defn look-at [eye center up]
  (let [m (.create js/mat4)]
    (.lookAt js/mat4 m (clj->js eye) (clj->js center) (clj->js up))
    m))

(defn transformMat4 [v m] ;;TODO multimethods on type
  (let [v' (.create js/vec4)]
    (.transformMat4 js/vec4 v' v m)
    v'))

(defn mult4 [a b] ;;TODO multimethod
  (let [m (.create js/mat4)]
    (.multiply js/mat4 m a b)
    m))

(defn rotate [m rad axis]
  (let [m' (.create js/mat4)]
    (.rotate js/mat4 m' m rad (clj->js axis))
    m'))
