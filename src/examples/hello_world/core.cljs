(ns examples.hello-world.core
  (:require [zoetrope.util.math :as math]
            [zoetrope.core :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.window :as io.window]
            [zoetrope.IO.dom :as io.dom]
            [zoetrope.IO.canvas :as io.canvas]))

(enable-console-print!)
  
(defn timer [t]
  (+ t 0.01))          

(defn rectangle [{:keys [window model]}]
  (let [t (:t model)
        width (:width window)
        height (:height window)]
      [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -100 :far 100}
       [:translate {:vector [(/ width 2) (/ height 2) 0]}
        [:rectangle {:origin [-100 -100 0 1] :width 200 :height 200 :fill "black"}] ;;TODO x y z
        [:translate {:vector [0 0 0]}
         [:rotate {:radians t :axis [0 1 0]}
          [:scale {:vector [1 1 1]}
           [:circle {:origin [100 100 0 1] :radius 10 :fill "red"}] 
           [:circle {:origin [100 -100 0 1] :radius 10 :fill "red"}] 
           [:circle {:origin [-100 100 0 1] :radius 10 :fill "red"}] 
           [:circle {:origin [-100 -100 0 1] :radius 10 :fill "red"}]]]]]])) 

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

