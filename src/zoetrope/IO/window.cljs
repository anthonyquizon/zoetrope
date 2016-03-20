(ns zoetrope.IO.window
    (:require))

(defn dimensions []
  {:width (.-innerWidth js/window)
   :height (.-innerHeight js/window)})
