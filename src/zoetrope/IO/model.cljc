(ns zoetrope.IO.model)

(defonce store (atom nil))

(defn component [init]
  (when (= @store nil) (reset! store init))
  (fn ([] @store)
      ([data] (reset! store data))))

