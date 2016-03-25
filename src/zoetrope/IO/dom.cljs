(ns zoetrope.IO.dom
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.match :refer-macros [match]]
            [cljsjs.virtual-dom]))

(declare vnode)

(defonce tree (atom (js/virtualDom.VText. "")))
(defonce root (atom (js/virtualDom.create @tree)))
(def app-id "app")

(defn vdom-h [tag attr child]
  (js/virtualDom.h (name tag) (clj->js attr) (clj->js child)))
 
(defn vnode [[tag & body]]
  (let [vchild #(map vnode %)]
    (match (vec body)
           []                                       (vdom-h tag {} [])
           [(a :guard map?)]                        (vdom-h tag a [])
           [(a :guard map?) (b :guard vector?) & c] (vdom-h tag a (vchild (cons b c)))
           [(a :guard map?) b]                      (vdom-h tag a b)
           [(a :guard vector?) & b]                 (vdom-h tag {} (vchild (cons a b)))
           [a]                                      (vdom-h tag {} a)
           :else                                    :no-match))) 

;;TODO rename as output-renderer
(defn renderer [] ;;TODO app-id
  (let [store (atom nil)]
    (dom/removeChildren root)
    (.appendChild (dom/getElement app-id) @root)
    (fn [{:keys [dom]}]
      (when (not= @store dom)
        (let [new-tree (vnode dom)
              patches (js/virtualDom.diff @tree new-tree)]
          (reset! tree new-tree)
          (reset! store dom)
          (swap! root js/virtualDom.patch patches))))))

