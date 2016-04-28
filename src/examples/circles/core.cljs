(ns examples.circles.core
  (:require [zoetrope.core :as z]
            [zoetrope.components :as z]
            [zoetrope.util.dom :as dom]
            [zoetrope.IO.model :as io.model]
            [zoetrope.IO.browser.window :as io.window]
            [zoetrope.IO.browser.dom :as io.dom]
            [zoetrope.IO.browser.canvas :as io.canvas]
            [examples.circles.components.scene]))

(enable-console-print!)

(z/run-io
  (z/resolve scene) 
  {:window (io.window/component)
   :dom (io.dom/component)
   :canvas (io.canvas/component)
   :model (io.model/component {:t 0})})
   
