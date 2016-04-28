(ns examples.circles.component.impl.view.stage
  (:require)) 

(defn dom [{:keys [window]}]
  [:canvas {:id "canvas" 
            :attributes {:width (window :width) 
                         :height (window :height)}}])

(defn canvas [input]
  [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -100 :far 100}])

(defn component [id]
  [[:>out/dom [dom input]]
   [:>out/canvas [canvas]]])
