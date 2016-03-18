(ns examples.cube-waves.core
    (:require [zoetrope.core :as z]
              [zoetrope.input.window :as window]
              [zoetrope.input.mouse :as mouse]
              [zoetrope.output.dom :as dom]))

(enable-console-print!)

(defn root [inputs state]
  {:dom [:h1 {} "hello world!"]})

;(z/run-io
  ;root 
  ;{:x 0
   ;:y 0}
  ;{:window {:dimensions window/dimensions}
   ;:mouse {:position mouse/position}}
  ;{:dom dom/renderer})
