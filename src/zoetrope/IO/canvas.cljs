(ns zoetrope.IO.canvas
  (:require [zoetrope.IO.impl.virtual :as v])) 

;; clear canvas
;; walk through each item
  ;; render

;; nesting terminal tags are the same as having them as siblings

(def example 
  [:transform {:matrix []} ;;TODO allow for 3d with multimethods
   [:rotation {:radians 3.14 :axis [0 1 0]}
     [:scale {:x 100 :y 200 :z 20}
      [:polygon {:points []}]
      [:line {:points []}]
      [:bezier]
      [:circle {:size 30}]
      [:rectangle {:w 100 :h 100 :r 5}]
      [:circle {:fill "red" :r 20}]]]])

(defn draw [context properties]
  (let [fill (:fill properties)]
    (when fill (.fillStyle context fill))))

(defn renderer [id]
  ;;init canvas
  (fn [{:keys [canvas]}]))
    
