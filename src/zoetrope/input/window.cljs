(ns zoetrope.input.window
    (:require))

(defn dimensions []
  (fn []
    {:width (.-innerWidth js/window)
     :height (.-innerHeight js/window)}))
