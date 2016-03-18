(ns zoetrope.input.window
    (:require))

(defn dimensions []
  {:width (.-innerWidth js/window)
   :height (.-innerHeight js/window)})
