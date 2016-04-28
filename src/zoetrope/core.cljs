(ns zoetrope.core
    (:require [com.rpl.specter :as s]))

;;TODO cross compile this

(defonce id (atom nil))

(defn- animate
    "Call f on the next animation frame."
    [f xs]
    (when @id (.cancelAnimationFrame js/window @id))
    (reset! id (.requestAnimationFrame js/window #(f xs))))

(defn- io-walk [fs & args] 
  (s/transform (s/walker fn?) #(apply % args) fs))

;;TODO schema
(defn run-io [f io-fs]
  (let [init (io-walk fs)]
    (animate (fn step [in]
               (let [out (io-walk fs in)]
                 (animate step out)))
             init)))
