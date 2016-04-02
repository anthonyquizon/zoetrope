(ns zoetrope.IO.impl.virtual
  #?(:clj  (:require [clojure.core.match :refer [match]]))
  #?(:cljs (:require [cljs.core.match :refer-macros [match]])))

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
