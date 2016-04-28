(ns zoetrope.IO.impl.virtual ;;TODO remove from impl
  #?(:clj  (:require [clojure.core.match :refer [match]]))
  #?(:cljs (:require [cljs.core.match :refer-macros [match]])))


;;TODO use spectre to walk
(defn render [f [tag & body]] 
  (let [render-child #(map (partial render f) %)]
    (match (vec body)
           []                                       (f tag {} [])
           [(a :guard map?)]                        (f tag a [])
           [(a :guard map?) (b :guard vector?) & c] (f tag a (render-child (cons b c)))
           [(a :guard map?) b]                      (f tag a b)
           [(a :guard vector?) & b]                 (f tag {} (render-child (cons a b)))
           [a]                                      (f tag {} a)
           :else                                    :no-match)))

;;TODO compile transform
(defn walk [f data] 
  (s/transform (s/walker) f data))
