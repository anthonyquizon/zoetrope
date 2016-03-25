(ns examples.cube-waves.core
  (:require [examples.math.core :as math]
            [zoetrope.core :as z]
            [zoetrope.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.window :as io.window]
            [zoetrope.IO.dom :as io.dom]))

(enable-console-print!)

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
        view-matrix (math/view-matrix width height)
        model-matrix (math/look-at [0 0 0] [0 0 -10] [0 1 0])
        matrix (math/mult4 view-matrix model-matrix)
        p1 (math/vector -200 -200 0 1)
        p2 (math/vector 200 -200 0 1)
        p3 (math/vector 200 200 0 1)
        p4 (math/vector -200 200 0 1)
        p1' (math/transformMat4 p1 matrix)
        p2' (math/transformMat4 p2 matrix)
        p3' (math/transformMat4 p3 matrix)
        p4' (math/transformMat4 p4 matrix)]

    (into (svg width height) 
          [(point (aget p1' 0) (aget p1' 1))
           (point (aget p2' 0) (aget p2' 1))
           (point (aget p3' 0) (aget p3' 1))
           (point (aget p4' 0) (aget p4' 1))])))
  
          
(defn root [input]
  {:dom (shapes input)}) 

(z/run-io
  root 
  {:mouse {:position (io.window/mouse-position)}
   :window io.window/dimensions}
  {:dom (io.dom/renderer)})
