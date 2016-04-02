(ns zoetrope.IO.browser.window
    (:require [goog.events :as events]))

(events/removeAll js/window)

;;TODO rename as input-dimensions
(defn dimensions []
  {:width (.-innerWidth js/window)
   :height (.-innerHeight js/window)})

;;TODO rename as input-mouse-position
(defn mouse-position []
  (let [state (atom nil)
        on-event #(reset! state {:x (.-clientX %) 
                                 :y (.-clientY %)})] 
    (events/listen js/window "mousemove" on-event) 
    (fn [] @state)))
                   
