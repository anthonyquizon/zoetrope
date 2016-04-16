(ns zoetrope.core
    (:require [com.rpl.specter :as s]
              [zoetrope.components :as c]))

;;TODO cross compile this

(defonce id (atom nil))

;;TODO platform independant interop of animation frame
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

(defn resolve [components main]
  (partial c/resolve components main))

(defn run-io [f inputs outputs]
  (frame-loop #(step f inputs outputs)))

