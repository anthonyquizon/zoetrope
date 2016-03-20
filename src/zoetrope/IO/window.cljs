(ns zoetrope.IO.window
    (:require [goog.events :as events]))

(events/removeAll js/window)

(defn dimensions []
  {:width (.-innerWidth js/window)
   :height (.-innerHeight js/window)})

(defn mouse-position []
  (let [state (atom nil)
        on-event #(reset! state {:x (.-clientX %) 
                                 :y (.-clientY %)})] 
    (events/listen js/window "mousemove" on-event) 
    (fn [] @state)))
                   
