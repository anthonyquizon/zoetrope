(ns examples.cube-waves.core
  (:require [examples.math.core :as math]
            [zoetrope.core :as z]
            [zoetrope.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.window :as io.window]
            [zoetrope.IO.dom :as io.dom]))

(enable-console-print!)

(.log js/console (math/matrix [[1 1] [1 0]]))

(defn view-matrix [width height]
  (let [aspect-ratio (/ width height)
        view-size 1 
        x (/ (* aspect-ratio view-size) 2)
        y (/ view-size 2)
        z 1000]
    (math/orthographic-matrix (- x) x y (- y) (- z) z))) 

(defn point [x y]
  [:circle (merge dom/svg-ns 
                  {:attributes {:cx x 
                                :cy y
                                :r 5}
                   :style {:fill "black"}})])
  
(defn svg [width height]
  (dom/svg {:attributes {:viewBox (str (- (/ width 2)) " " (- (/ height 2)) " " width " " height)}
            :style {:position "absolute"
                    :width "100%" 
                    :height "100%"}}))

(defn shapes [input]
  (let [width  (get-in input [:window :width]) 
        height (get-in input [:window :height])
        matrix (view-matrix width height)
        p1 (math/vector [0 0 0 1])
        p2 (math/vector [200 100 0 1])
        p1' (.multiply js/math p1 matrix)
        p2' (.multiply js/math p2 matrix)]
    ;(print p2')
    (into (svg width height) 
          [(point (math/index p1' 0) (math/index p1' 1))
           (point (math/index p2' 0) (math/index p2' 1))])))
  
          
(defn root [input]
  {:dom (shapes input)}) 

(z/run-io
  root 
  {:mouse {:position (io.window/mouse-position)}
   :window io.window/dimensions}
  {:dom (io.dom/renderer)})
