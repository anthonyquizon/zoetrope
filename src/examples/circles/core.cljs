(ns examples.circles.core
  (:require [zoetrope.core :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.browser.window :as io.window]
            [zoetrope.IO.browser.dom :as io.dom]
            [zoetrope.IO.browser.canvas :as io.canvas]))

(enable-console-print!)

(defn canvas [{:keys [window]}]
  [:orthographic {:left -1 :right 1 :top 1 :bottom -1 :near -100 :far 100}
   [:translate {:vector [(-> window :width (/ 2)) (-> window :height (/ 2)) 0]}
    [:circle {:origin [-100 -100 0] :radius 100  :line-width 5 :stroke "black"}]]])

(defn dom [{:keys [window]}]
  [:canvas {:id "canvas" 
            :attributes {:width (window :width) 
                         :height (window :height)}}])

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
