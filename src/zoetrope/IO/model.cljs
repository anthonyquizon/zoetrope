(ns zoetrope.IO.model)

;;TODO single 
(defonce store (atom nil))

(defn input [init]
  (when (= @store nil)
    (reset! store init))
  (fn [] @store))

(defn output [{:keys [model]}]
  (reset! store model))

;;closure to return function with IO
