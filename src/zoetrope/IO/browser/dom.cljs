(ns zoetrope.IO.browser.dom
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [zoetrope.util.virtual :as v]
            [cljsjs.virtual-dom]))

(defonce tree (atom (js/virtualDom.VText. "")))
(defonce root (atom (js/virtualDom.create @tree)))

(defn vdom-h [tag attr child]
  (js/virtualDom.h (name tag) (clj->js attr) (clj->js child)))
 
;;TODO rename as output-renderer
(defn component [dom-id]
  (let [store (atom nil)]
    (dom/removeChildren root)
    (.appendChild (dom/getElement dom-id) @root)
    (fn [dom]
      (when (not= @store dom)
        (let [new-tree (v/render vdom-h dom)
              patches (js/virtualDom.diff @tree new-tree)]
          (reset! tree new-tree) 
          (reset! store dom)
          (swap! root js/virtualDom.patch patches))))))
