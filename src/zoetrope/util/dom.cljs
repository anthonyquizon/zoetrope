(ns zoetrope.util.dom)

;;TODO move elsewhere? or delete?

(def svg-ns {:namespace "http://www.w3.org/2000/svg"})

(defn svg [properties & children]
  (into [:svg (merge svg-ns properties)] children))

