(ns zoetrope.IO.browser.canvas
  (:require [zoetrope.IO.impl.virtual :as v]
            [zoetrope.util.math :as math]
            [goog.dom :as dom])) 

(defn format-tree [tag attr children]
  {:tag tag :attr attr :children children})

(defn set-style [context fill stroke] 
  (when fill (aset context "fillStyle" fill))
  (when stroke (aset context "strokeStyle" stroke)))
  
(defn transform-vector [v m] ;;TODO move this
  (let [v' (-> (conj v 1) math/vector (math/transformMat4 m))]
    [(aget v' 0) (aget v' 1)]))


(defmulti draw (fn [tag _ _ _] tag))

(defmethod draw :default [_ _ _ _]) 
 
;(defmethod draw :cube [_ _ _]) 

(defmethod draw :rectangle 
  [_ context matrix {:keys [origin width height fill stroke]}]  ;;TODO default coords - z = 0
  (let [[x y] (transform-vector origin matrix)]
    ;;TODO rounded corners
    (set-style context fill stroke)
    (when fill (.fillRect context x y width height))
    (when stroke (.strokeRect context x y width height))))

(defmethod draw :circle
  [_ context matrix attr]
  (let [PI-2 (* 2 (.-PI js/Math)) 
        attr' (assoc attr :start-angle 0 :end-angle PI-2)]
    (draw :arc context matrix attr')))

(defmethod draw :arc
  [_ context matrix {:keys [origin radius start-angle end-angle fill stroke]}]
  (let [[x y] (transform-vector origin matrix)]
    (set-style context fill stroke)
    (.beginPath context)
    (.arc context x y radius start-angle end-angle)
    (when fill (.fill context))
    (when stroke (.stroke context))
    (.closePath context)))


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

(defn renderer 
  ([canvas-id] (renderer canvas-id {:clear-colour "white"}))
  ([canvas-id {:keys [clear-colour]}]
   (let [store (atom nil)
         width (atom nil)
         height (atom nil)]
     (fn [{:keys [canvas]}]
       (when-let [elem (dom/getElement canvas-id)] ;;TODO check if canvas exists
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

