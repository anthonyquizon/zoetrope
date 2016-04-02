(ns examples.squares.core
  (:require [zoetrope.core :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.window :as io.window]
            [zoetrope.IO.dom :as io.dom]
            [zoetrope.IO.canvas :as io.canvas]))

(enable-console-print!)

(defn canvas [{:keys [window]}]
  [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -100 :far 100}
   [:translate {:vector [(-> window :width (/ 2)) (-> window :height (/ 2)) 0]}
    [:rectangle {:origin [-100 -100 0] :width 200 :height 200 :fill "black"}]]])

(defn dom [{:keys [window]}]
  [:canvas {:id "canvas" 
            :attributes {:width (-> window :width (/ 2)) 
                         :height (-> window :height (/ 2))}}])

(defn root [input]
  {:dom (dom input)
   :canvas (canvas input)})

(z/run-io
  root 
  {:mouse {:position (io.window/mouse-position)}
   :window io.window/dimensions
   :model (io.model/input {:t 0})}
  {:dom (io.dom/renderer "app")
   :canvas (io.canvas/renderer "canvas")
   :model io.model/output})
