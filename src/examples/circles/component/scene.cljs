(ns examples.circles.component.scene
  (:require [zoetrope.component :refer [|]]
            [examples.circles.component.impl.view.stage :as stage]))


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

;;TODO think of graph -> intercomponent communication

(defn circle []
  [[]]) ;;TODO

;; Advantages of vector syntax
;;   no need to pull out io/components
;;       :<in, :>out are handled by the resolve function
;;   no need to explicitly pass the inputs for each function
;;      :<in :>out allow for data access
;;   automatic separation of :<out data
;;      :>out/model [:>out/dom ...] [:>out/canvas ...] [:>out/model ...] ;;will only take the out model and give runtime warning
;; will give runtime warning if :<out is on the left hand side
;; will give runtime warning if :<in is on the right hand side
;; like spreadsheets
(defn component [n]
  [[stage/component ;; :. => identity keyword function  - do not do anything
    [:>out/dom [circle {:x 1 :y 2 :z :<in/dom}]]
    [circle {:x :<in/model/x :y 2 :z}] 
    [circle {:x 1 :y 2 :z}] 
    [circle {:x 1 :y 2 :z}]]]) 

