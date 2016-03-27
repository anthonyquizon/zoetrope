(ns zoetrope.IO.canvas
  (:require [zoetrope.IO.impl.virtual :as v]
            [zoetrope.util.math :as math]
            [goog.dom :as dom])) 

(defn format-tree [tag attr children]
  {:tag tag :attr attr :children children})

(defn set-style [context fill stroke] 
  (when fill (aset context "fillStyle" fill))
  (when stroke (aset context "strokeStyle" stroke)))
  
(defn transform-vector [v m] ;;TODO move this
  (let [v' (-> v math/vector (math/transformMat4 m))]
    [(aget v' 0) (aget v' 1)]))

(defmulti render-tag (fn [_ _ tag _] tag))

(defmethod render-tag :default [_ _ _]) 

;;stubs TODO
(defmethod render-tag :sphere [_ _ _]) 
(defmethod render-tag :cube [_ _ _]) 

(defmethod render-tag :rectangle 
  [context matrix _ {:keys [origin width height fill stroke]}]  ;;TODO default coords - z = 0
  (let [[x y] (transform-vector origin matrix)]
    (set-style context fill stroke)
    (when fill (.fillRect context x y width height))
    (when stroke (.strokeRect context x y width height))))

(defmethod render-tag :circle
  [context matrix _ {:keys [origin radius fill stroke]}]
  (let [[x y] (transform-vector origin matrix)]
    (set-style context fill stroke)
    (.beginPath context)
    (.arc context x y radius 0 (* 2 (.-PI js/Math)))
    (when fill (.fill context))
    (when stroke (.stroke context))
    (.closePath context)))

(defmethod render-tag :orthographic
  [context matrix _ {:keys [left right top bottom near far]}]
  (let [matrix' (math/orthographic-matrix left right top bottom near far)]
    (math/mult4 matrix' matrix)))

(defmethod render-tag :rotate
  [context matrix _ {:keys [radians axis]}]
  (math/rotate matrix radians axis))

(defn render 
  ([context node] (render context (math/matrix) node))
  ([context matrix {:keys [tag attr children]}]
   (let [output (render-tag context matrix tag attr)
         matrix' (if output output matrix)]
     (doseq [child children] 
       (render context matrix' child)))))

(defn clear-canvas [elem context colour width height]
  (render-tag context (math/matrix) :rectangle {:x 0 :y 0 :width width :height height :fill colour}))

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

