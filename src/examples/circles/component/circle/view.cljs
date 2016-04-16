(ns examples.circles.component.circle.view)

(defn start [])

(defn update [t xoffs y bounds]
  (let [x (+ (* -50 t) xoffs)
        z 0]
    [:circle {:origin [x y z] :radius 30 :line-width 10 :stroke "black"}]))

(defn render [input]
  [:circle {:origin [200 200 200] :radius 40 :fill (colours :black)}]
  #_(vec (concat [:translate {:vector [0 0 0]}] 
                (circle-array t 0 10 bounds))))

(defn component []
  {:dom start})
   
