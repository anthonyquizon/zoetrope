(ns examples.circle-mouse.core
  (:require [com.rpl.specter :as s]
            [zoetrope.core :as z]
            [zoetrope.IO.window :as window]
            [zoetrope.IO.dom :as dom]))

(enable-console-print!)

(defn circle [x y]
  [:circle 
   (merge dom/svg-ns 
          {:attributes {:r 100} 
           :style      {:cx x
                        :cy y
                        :stroke-width 15
                        :fill "none"
                        :stroke "black"}})])

(defn svg [x y]
  (dom/svg {:style {:position "absolute"
                    :cursor "none"
                    :width "100%" 
                    :height "100%"}}
           (circle x y)))
  
(defn root [inputs]
  (let [{:keys [x y]} (get-in inputs [:mouse :position])]
    {:dom (svg x y)})) 

(z/run-io
  root 
  {:mouse {:position (window/mouse-position)}}
  {:dom (dom/renderer)})
