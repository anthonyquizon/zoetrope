;;TODO make cljc

(ns zoetrope.component)
  

(defn resolve [components main])

(def example {:dom [:div 
                    [[:h1 "heading"]
                     [:h2 "subheading"]
                     [:content 
                      {:dom [:p "hello world"]
                       :canvas [:circle {:x 100 :y 100}] 
                       :audio []}]]]}) 


(defn example-component-child []
  {:dom [:p "child"]
   :canvas [:circle]})
  
(defn example-component [] 
  (let [child (example-component-child)])

  {:dom [:div 
         [[:p "woo"
           (:dom child)]]]
   :canvas [:group (:canvas child)]})
                         
