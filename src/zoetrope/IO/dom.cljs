(ns zoetrope.IO.dom
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [zoetrope.IO.impl.virtual :as v]
            [cljsjs.virtual-dom]))

(defonce tree (atom (js/virtualDom.VText. "")))
(defonce root (atom (js/virtualDom.create @tree)))
(def app-id "app")

(defn vdom-h [tag attr child]
  (js/virtualDom.h (name tag) (clj->js attr) (clj->js child)))
 
;;TODO rename as output-renderer
(defn renderer [] ;;TODO app-id
  (let [store (atom nil)]
    (dom/removeChildren root)
    (.appendChild (dom/getElement app-id) @root)
    (fn [{:keys [dom]}]
      (when (not= @store dom)
        (let [new-tree (v/render vdom-h dom)
              patches (js/virtualDom.diff @tree new-tree)]
          (reset! tree new-tree)
          (reset! store dom)
          (swap! root js/virtualDom.patch patches))))))

