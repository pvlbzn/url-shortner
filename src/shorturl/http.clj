(ns shorturl.http
  (:require [ring.adapter.jetty :as ring-jetty]
            [ring.util.response :as r]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [shorturl.db :as db]
            [shorturl.slug :as s]))


; HTTP status codes
(def not-found {:code 404 :message "not found"})
(def temporary-redirect {:code 307 :message "temporary redirect"})

(defn redirect [req]
  (let [slug (get-in req [:path-params :slug])
        url  (:url (db/get-by-slug slug))]
    (if url
      (r/redirect url (:code temporary-redirect))
      (r/not-found (:message not-found)))))

(defn- create-new-redirect [url]
  (let [slug (s/rnd1 s/default-length)]
    (db/inser-redirect! slug url)
    (r/response (str "create slug " slug))))


(defn create-redirect
  [req]
  (let [url (get-in req [:body-params :url])
        obj (db/get-by-url url)]
    (if obj
      (r/response (str {:message (str "already exists, use slug " (:slug obj))}))
      (create-new-redirect url))))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     [":slug" redirect]
     ["api/"
      ["redirect" {:post create-redirect}]]
     ["" {:handler (fn [req] {:body "URL shortner" :status 200})}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring-jetty/run-jetty #'app {:port 3101
                         :join? false}))

(def server (start))
(.stop server)
