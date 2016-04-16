(ns examples.circles.circle.component
  (:require [examples.circles.component.circle.control :as control] 
            [examples.circles.component.circle.view :as view])) 

;;Only bottom components contain implementation - concrete types
;;    eg. :dom or :canvas
(defn component []
  {:view view/component
   :control control/component})
   
