(ns examples.circles.components
  (:require [examples.circles.component.circle :as circle]))


(defn canvas [input]
  [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -100 :far 100}])

(defn dom [{:keys [window]}]
  [:canvas {:id "canvas" 
            :attributes {:width (window :width) 
                         :height (window :height)}}])

(defn components [input] 
  {:circle circle/component
   :canvas canvas
   :dom [dom [:canvas]]
   :circles [[:circle 1 1 0]
             [:circle 1 1 0]
             [:circle 1 1 0]]
   :main :dom}) 
