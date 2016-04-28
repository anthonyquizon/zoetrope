(ns zoetrope.IO.browser.canvas
  (:require [zoetrope.util.virtual :as v]
            [zoetrope.util.math :as math]
            [goog.dom :as dom])) 

(defn format-tree [tag attr children]
  {:tag tag :attr attr :children children})

;;TODO split into smaller functions 
(defn set-style 
  [context {:keys [fill-fn stroke-fn]} {:keys [fill stroke line-width]}] 
  (let [line-width' (if line-width line-width 1)]
      
    (when fill 
      (aset context "fillStyle" fill)
      (fill-fn))
    
    ;;TODO line caps

    (aset context "lineWidth" line-width)

    (when stroke 
      (aset context "strokeStyle" stroke)
      (stroke-fn))))
  
(defn transform-vector [v m] ;;TODO move this
  (let [v' (-> (conj v 1) math/vector (math/transformMat4 m))]
    [(aget v' 0) (aget v' 1)]))


(defmulti draw (fn [tag _ _ _] tag))

(defmethod draw :default [_ _ _ _]) 
 
;(defmethod draw :cube [_ _ _]) 

(defmethod draw :rectangle 
  [_ context matrix {:keys [origin width height] :as attr}]  ;;TODO default coords - z = 0
  (let [[x y] (transform-vector origin matrix)]
    ;;TODO rounded corners
    (set-style context 
               {:fill-fn #(.fillRect context x y width height)
                :stroke-fn #(.strokeRect context x y width height)}
               attr)))

(defmethod draw :circle
  [_ context matrix {:keys [origin radius start-angle end-angle] :as attr}]
  (let [start-angle' (if start-angle start-angle 0)
        end-angle' (if end-angle end-angle math/PI-double)
        [x y] (transform-vector origin matrix)]

    (.beginPath context)

    (when (not= end-angle' math/PI-double)
      (.lineTo context x y))
    
    (.arc context x y radius start-angle' end-angle')

    (when (not= end-angle' math/PI-double)
      (.lineTo context x y))

    (.closePath context)
    
    (set-style context {:fill-fn #(.fill context)
                        :stroke-fn #(.stroke context)}
               attr)))


(defmulti transform (fn [tag _ _] tag))

(defmethod transform :default [_ matrix _] matrix) 

(defmethod transform :orthographic [_ matrix {:keys [left right top bottom near far]}]
  (let [matrix' (math/orthographic-matrix left right top bottom near far)]
    (math/mult4 matrix' matrix)))

(defmethod transform :scale [_ matrix {:keys [vector]}] 
  (math/scale matrix vector))

(defmethod transform :translate [_ matrix {:keys [vector]}] 
  (math/translate matrix vector))

(defmethod transform :rotate [_ matrix {:keys [radians axis]}] 
  (math/rotate matrix radians axis))

(defn render 
  ([context node] (render context (math/matrix) node))
  ([context matrix {:keys [tag attr children]}]
   (let [matrix' (transform tag matrix attr)]
     (draw tag context matrix' attr)
     (doseq [child children] 
       (render context matrix' child)))))

(defn clear-canvas [elem context colour width height]
  (draw :rectangle context (math/matrix) {:origin [0 0 0 1] :width width :height height :fill colour}))

(defn create-renderer 
  ([canvas-id] (renderer canvas-id {:clear-colour "white"}))
  ([canvas-id {:keys [clear-colour]}]
   (let [store (atom nil)
         width (atom nil)
         height (atom nil)]
     (fn [input]
       (when-let [elem (dom/getElement canvas-id)]
             (let [context (.getContext elem "2d")
                   width' (.-width elem) 
                   height' (.-height elem)] 
               (when (or (not= @store canvas) 
                         (not= @width width') 
                         (not= @height height'))
                 (let [tree (v/render format-tree canvas)]
                   (clear-canvas elem context clear-colour width' height')
                   (render context tree)

                   (reset! store canvas)
                   (reset! width width')
                   (reset! height height')))))))))

(defn component [canvas-id props]
  (let [renderer (create-renderer canvas-id props)]
    {:>out renderer}))
