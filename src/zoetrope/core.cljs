(ns zoetrope.core
    (:require [zoetrope.output.dom :as dom]
              [com.rpl.specter :as s]))

(defonce id (atom nil))
(defonce state (atom nil))

(defn- animate
    "Call f on the next animation frame."
    [f]
    (when @id (.cancelAnimationFrame js/window @id))
    (reset! id (.requestAnimationFrame js/window f)))

(defn- apply-if [p f args]
  (if (p f) (apply f args) f))

(defn- walk-apply [fs & args] 
  (s/transform (s/walker fn?) #(apply % args) fs))

(defn- step [f inputs state outputs]
  (let [in (walk-apply inputs)
        out (f in @state)]
    (walk-apply outputs out)
    (reset! state (:model out))))

(defn- frame-loop [f]
  (animate (fn tick [t]
             (f)
             (animate tick))))

(defn run-io [f init-state inputs outputs]
  (reset! state init-state)
  (frame-loop #(step f inputs state outputs)))

