(ns examples.circles.core
  (:require [schema.core :as sc]
            [zoetrope.core :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.browser.window :as io.window]
            [zoetrope.IO.browser.dom :as io.dom]
            [zoetrope.IO.browser.canvas :as io.canvas]
            [examples.circles.components :refer [components]]))

(enable-console-print!)

(z/run-io
  (z/resolve components :main)
  {:mouse {:position (io.window/mouse-position)}
   :window io.window/dimensions
   :model (io.model/input {:t 0})}
  {:dom (io.dom/renderer "app")
   ;;TODO events
   :canvas (io.canvas/renderer "canvas")
   :model io.model/output})
