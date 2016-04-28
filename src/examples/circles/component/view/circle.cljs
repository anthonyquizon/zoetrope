(ns examples.circles.component.impl.view.circle
  (:require)) 


(defn component [x y z name]
  [[:>out/model {:name name}] 
   [:view (view (+ :<in/window/mouse/x x))]
   [:control {}]])
