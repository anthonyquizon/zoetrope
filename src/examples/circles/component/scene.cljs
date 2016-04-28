(ns examples.circles.component.scene
  (:require [examples.circles.component.impl.view.stage :as stage]))


;;;TODO paramerterized model access
;(defn circle [input]
  ;(let [transform {:io/canvas [:translate {:vector 1 2 3}]}
        ;bottom (circle/component input)
        ;top (circle/component input)])
  ;[transform top bottom])

;;pass and restrict input or 

;;get only what you stored
;;left-hand io is output
;;right-hand io is input

;;stored data can only be accessed if stored by component
;;TODO cyclejs -> see how they pass down model with encapsulation
;make todo mvc


;;TODO execute on timed interval
;; assign animation curves to svg elements
;; update position

(defn component [n]
  [[:width :<in/canvas]
   [:stage (stage/component)]
   [:circles [(circle/component {:x 1 :y 2 :z})]]]) 
