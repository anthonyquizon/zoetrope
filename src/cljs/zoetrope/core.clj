(ns zoetrope.core
    (:require [zoetrope.dom :as dom]
              [clojure.walk :as w]
              [clojure.test :as t]))

(defonce id (atom nil))
(defonce state (atom nil))

(defn- animate
    "Call f on the next animation frame."
    [f]
    (when @id (.cancelAnimationFrame js/window @id))
    (reset! id (.requestAnimationFrame js/window f)))

(defn run-renderers [renderers state]
  (doseq [r renderers] (r state)))

(defn update-inputs [inputs]
  (doseq [i inputs] (i)))

(defn step [inputs state renderers]
  (let [in (update-inputs inputs)
        out (f in @state)]
    (run-renderers out)
    (reset! state out)))

(defn frame-loop [f]
  (animate (fn tick [t]
             (f)
             (animate tick))))

(defn call-if [f]
  (if (function? f) (f) f))

(defn init-io [fs]
  (let [value-fn (fn [[k v]] [k (call-if v)])]
    (w/walk value-fn identity fs)))

(defn run-io [f init-state inputs outputs]
  (let [inputs' (init-io inputs)
        outputs' (init-io outputs)]
    (reset! state init-state)
    (frame-loop #(step inputs state renderers)))
