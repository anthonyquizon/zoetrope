(ns zoetrope.component
  (:require [zoetrope.util.virtual :as v]
            [com.rpl.specter :as s]))

(def | identity)

;;TODO cache macro
;;TODO assertions

(defn- resolve' [components input])
  
(defn resolve [components]
  #(resolve' components %))
  
;;TODO unit test
;;cases -> output vform is not purley output

