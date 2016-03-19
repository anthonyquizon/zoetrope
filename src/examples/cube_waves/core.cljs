(ns examples.cube-waves.core
  (:require [zoetrope.core :as z]
            [zoetrope.input.window :as window]
            [zoetrope.input.mouse :as mouse]
            [zoetrope.output.dom :as dom]))

(enable-console-print!)

(defn circle []
  [:circle 
   (merge dom/svg-ns 
          {:attributes {:r 10} 
           :style      {:stroke-width 5
                        :fill "red"
                        :stroke "black"}}
          [])])

(defn svg []
  (dom/svg {:attributes {:width 500 :height 500}} circle))
  
(defn root [inputs state]
  {:dom (into [:div] (map (fn [x] [:h1 (str "number " x)]) (range 0 1000)))
  ;{:dom [:div {:style {:color "red"}} [:h1 {:style {:margin-top "20%" :font-size 70}} "hello!"]]}
  ;{:dom [:div {:style {:color "red"}} [:h1 {:style {:margin-top "20%" :font-size 70}} "hello!"] [:h2 {:style {:color "blue"}} "World!"]]
  ;{:dom [:div "hello"] 
  ;{:dom [:div {:style {:width "20px" :height "20px" :background "red"}}] 
  ;{:dom [:div]
   :model {:x (get-in inputs [:window :dimensions :width]) :y 1}})

(z/run-io
  root 
  {:x 0
   :y 0}
  {:window {:dimensions window/dimensions}
   :mouse {:position (mouse/position)}}
  {:dom (dom/renderer)})
