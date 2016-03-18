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
      (clj->js (map vnode child)))
    child))

(defn- vnode [[nnode props child]]
  (js/virtualDom.h (name nnode) (clj->js props) (vchild child)))

(defn renderer []
  (dom/removeChildren root)
  (.appendChild (dom/getElement app-id) @root)
  (fn [{:keys [dom]}]
    (let [new-tree (vnode dom)
          patches (js/virtualDom.diff @tree new-tree)]
      (reset! tree new-tree)
      (swap! root js/virtualDom.patch patches))))
