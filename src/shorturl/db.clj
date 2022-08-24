(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer [select from insert-into columns values where]]
            [shorturl.env :refer [env]]))


(def ^:private mysql-db {:host (env :HOST)
                         :dbtype (env :DBTYPE)
                         :dbname (env :DBNAME)
                         :user (env :USER)
                         :password (env :PASSWORD)})

(defn- query
  [q]
  (j/query mysql-db q))

(defn- select-where
  [op sym value]
  (query (-> (select :*)
             (from :redirects)
             (where [op sym value])
             (sql/format))))

(defn- insert! 
  [q]
  (j/db-do-prepared mysql-db q))

(defn inser-redirect!
  [slug url]
  (insert! (-> (insert-into :redirects)
               (columns :slug :url)
               (values [[slug url]])
               (sql/format))))

(defn get-by-slug
  [slug]
  ; Here `:url` function is the same as
  ; (get (first (get-url slug)) :url)
  (first (select-where := :slug slug)))

(defn get-by-url
  [url]
  (first (select-where := :url url)))
