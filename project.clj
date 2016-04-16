(defproject zoetrope "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "" :url ""}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.40"]
                 [org.clojure/test.check "0.9.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [prismatic/schema "1.1.0"]
                 [prismatic/schema-generators "0.1.0"]
                 [com.rpl/specter "0.9.2"]
                 [cljsjs/wad "2.2.3-0"]
                 [cljsjs/virtual-dom "2.1.1-0"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]

  :aliases {"up" ["figwheel" "examples"]}

  :source-paths  ["src"]

  :cljsbuild {:builds
              [{:id "examples"
                :source-paths ["src/zoetrope" "src/examples"]
                :figwheel true
                :compiler {:asset-path "js/compiled/out"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map true
                           :optimizations :none}}]}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:server-port 8881})
