(ns zoetrope.core
    (:require [com.rpl.specter :as s]))

(defonce id (atom nil))

(defn- animate
    "Call f on the next animation frame."
    [f]
    (when @id (.cancelAnimationFrame js/window @id))
    (reset! id (.requestAnimationFrame js/window f)))

(defn- walk-apply [fs & args] 
  (s/transform (s/walker fn?) #(apply % args) fs))

(defn- step [f inputs outputs]
  (let [out (-> inputs walk-apply f)]
    (walk-apply outputs out)))

(defn- frame-loop [f]
  (animate (fn tick [t]
             (f)
             (animate tick))))

(defn run-io [f inputs outputs]
  (frame-loop #(step f inputs outputs)))

