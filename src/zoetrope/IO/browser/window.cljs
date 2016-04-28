(ns zoetrope.IO.browser.window
    (:require [goog.events :as events]))


(defn- dimensions []
  {:width (.-innerWidth js/window)
   :height (.-innerHeight js/window)})

(defn- mouse-input []
  (let [state (atom nil)
        on-event #(reset! state {:x (.-clientX %) 
                                 :y (.-clientY %)})] 
    (events/listen js/window "mousemove" on-event) 
    (fn [] @state)))

(defn component []
  (events/removeAll js/window)
  (let [mouse-fn (mouse-input)]
    {::input (fn []
               {:mouse (mouse-fn)
                :dimensions (dimensions)})}))
