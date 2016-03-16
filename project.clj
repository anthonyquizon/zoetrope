(defproject zoetrope "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "" :url ""}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/test.check "0.9.0"]
                 [cljsjs/wad "2.2.3-0"]
                 [cljsjs/virtual-dom "2.1.1-0"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]

  :source-paths ["src/cljs"]

  :aliases {"up" ["figwheel" "examples"]}

  :cljsbuild {:builds
              ;;TODO test
              [{:id "examples"
                :source-paths ["src/cljs"]
                :figwheel true
                :compiler {:main zeotrope.core
                           :asset-path "/js/compiled/out"
                           :output-to "resources/public/js/compiled/zeotrope.js"
                           :output-dir "resources/public/js/compiled/out"
                           :optimizations :none
                           :source-map-timestamp true}}]}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler zoetrope.server/handler
             :server-port 8881})
