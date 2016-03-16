(ns zoetrope.server
    (:require [ring.util.response :as res]
              [ring.middleware.reload :as reload]
              [ring.middleware.resource :as resource]
              [bidi.ring]))

;;TODO set javascript name
(defn response-page
    [filename]
    (-> filename
        (res/resource-response {:root "public"})
        (res/content-type "text/html")))

(def routes
    ["/" [["" (response-page "index.html")]]])

(def handler
    (-> (bidi.ring/make-handler routes)
        (resource/wrap-resource "public")
        (reload/wrap-reload)))
