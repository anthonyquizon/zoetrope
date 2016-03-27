(ns zoetrope.IO.canvas
  (:require [zoetrope.IO.impl.virtual :as v]
            [goog.dom :as dom])) 

;; clear canvas
;; walk through each item
  ;; render

;; nesting terminal tags are the same as having them as siblings
;;TODO build up functions and execute in order
(defn format-tree [tag attr children]
  {:tag tag :attr attr :children children})

(defn set-style [context fill stroke] 
  (when fill (aset context "fillStyle" fill))
  (when stroke (aset context "strokeStyle" stroke)))
  
(defn rectangle [context {:keys [x y width height fill stroke]}] ;;TODO left right?
  (set-style context fill stroke)
  (when fill (.fillRect context x y width height))
  (when stroke (.strokeRect context x y width height)))

(defn circle [context {:keys [x y radius fill stroke]}]
  (set-style context fill stroke)
  (.beginPath context)
  (.arc context x y radius 0 (* 2 (.-PI js/Math)))
  (when fill (.fill context))
  (when stroke (.stroke context))
  (.closePath context))

(defn render [context {:keys [tag attr children]}]
  (case tag
    :rectangle (rectangle context attr)
    :circle (circle context attr)
    :default)
  (doseq [child children] 
    (render context child)))
  
(defn clear-canvas [elem context colour]
  (let [width (.-width elem)
        height (.-height elem)]
    (rectangle context {:x 0 :y 0 :width width :height height :fill colour})))

;;TODO clear colour
(defn renderer 
  ([canvas-id] (renderer canvas-id {:clear-colour "white"}))
  ([canvas-id {:keys [clear-colour]}]
   (let [store (atom nil)]
     (fn [{:keys [canvas]}]
       (let [elem (dom/getElement canvas-id)]
         (when (and (not= @store canvas) elem)
           (let [context (.getContext elem "2d")
                 tree (v/render format-tree canvas)]
             (print tree)
             (clear-canvas elem context clear-colour)
             (render context tree)
             (reset! store canvas))))))))

