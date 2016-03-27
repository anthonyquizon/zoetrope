(ns examples.hello-world.core
  (:require [zoetrope.util.math :as math]
            [zoetrope.core :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.window :as io.window]
            [zoetrope.IO.dom :as io.dom]
            [zoetrope.IO.canvas :as io.canvas]))

(enable-console-print!)

;(defn point [x y]
  ;[:circle (merge dom/svg-ns 
                  ;{:attributes {:cx x 
                                ;:cy y
                                ;:r 5}
                   ;:style {:fill "black"}})])
  
;(defn svg [width height]
  ;(dom/svg {:attributes {:viewBox (str (- (/ width 2)) " " (- (/ height 2)) " " width " " height)}
            ;:style {:position "absolute"
                    ;:width "100%" 
                    ;:height "100%"}}))

;(defn cubes [input]
  ;(let [t (get-in input [:model :t])
        ;cube (math/cube-points 2 1 1)
        ;view-matrix (math/orthographic-matrix -0.01 0.01 0.01 -0.01 -10 10)
        ;model-matrix (math/matrix)
        ;rotated-model (math/rotate model-matrix (mod t (* (.-PI js/Math) 2)) [0 1 0])
        ;matrix (math/mult4 view-matrix rotated-model)]
    ;(map (fn [p] (let [p' (math/transformMat4 p matrix)]
                   ;(point (aget p' 0) (aget p' 1)))) cube)))

;(defn shapes [input]
  ;(let [width  (get-in input [:window :width]) 
        ;height (get-in input [:window :height])]
    ;(into (svg width height) (cubes input)))) 
  
(defn timer [t]
  (+ t 0.01))          

;(defn root [input]
  ;{:dom (shapes input)
   ;:model {:t (timer (get-in input [:model :t]))}})

;(z/run-io
  ;root 
  ;{:mouse {:position (io.window/mouse-position)}
   ;:window io.window/dimensions
   ;:model (io.model/input {:t 0})}
  ;{:dom (io.dom/renderer)
   ;:model io.model/output})

;;TODO x y z as array?
(defn orthographic-matrix [width height]
  (let [aspect-ratio (/ width height)
        view-size 1]
    [:orthographic]))

(defn rectangle [{:keys [window model]}]
  (let [t (:t model)]
    [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -10 :far 10}
     [:transform {:matrix []}
      [:rectangle {:origin [500 400 100 1] :width 200 :height 200 :fill "black"}]] ;;TODO x y z
     [:rotate {:radians t :axis [0 1 0]}
      [:circle {:origin [400 300 0 1] :radius 100 :fill "red"}] 
      [:circle {:origin [600 300 0 1] :radius 100 :fill "red"}] 
      [:circle {:origin [600 500 0 1] :radius 100 :fill "red"}] 
      [:circle {:origin [400 500 0 1] :radius 100 :fill "red"}]]])) 

(defn canvas [{:keys [window]}]
  [:canvas {:id "canvas" 
            :attributes {:width (window :width) 
                         :height (window :height)}}]) 

(defn root [input]
  {:dom (canvas input)
   :canvas (rectangle input)
   :model {:t (timer (get-in input [:model :t]))}})

(z/run-io
  root 
  {:mouse {:position (io.window/mouse-position)}
   :window io.window/dimensions
   :model (io.model/input {:t 0})}
  {:dom (io.dom/renderer "app")
   :canvas (io.canvas/renderer "canvas")
   :model io.model/output})

