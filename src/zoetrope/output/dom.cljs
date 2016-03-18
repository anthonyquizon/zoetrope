(ns zoetrope.output.dom
    (:require [goog.dom :as dom]
              [cljsjs.virtual-dom]))

(declare vnode)
(defonce tree (atom (js/virtualDom.VText. "")))
(defonce root (atom (js/virtualDom.create @tree)))
(def app-id "app")
(def svg-ns {:namespace "http://www.w3.org/2000/svg"})

(defn svg [properties children]
    [:svg (merge svg-ns properties) [children]])

(defn- vchild [child]
    (if (vector? child)
        (if (keyword? (first child))
            (vnode child)
            (clj->js (map vnode child)))
        child))

(defn- vnode [[nnode props child]]
    (let [child' (vchild child)
          props' (clj->js props)
          nname' (name nnode)]
        (js/virtualDom.h nname' props' child')))

(defn renderer []
    (dom/removeChildren root)
    (.appendChild (dom/getElement app-id) @root)
    (fn [view]
        (let [new-tree (vnode view)
              patches (js/virtualDom.diff @tree new-tree)]
            (reset! tree new-tree)
            (swap! root js/virtualDom.patch patches))))
