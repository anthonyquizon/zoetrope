(ns zoetrope.dom)

(def svg-ns {:namespace "http://www.w3.org/2000/svg"})

(defn svg [properties & children]
  (into [:svg (merge svg-ns properties)] children))

