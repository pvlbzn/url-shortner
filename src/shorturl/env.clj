(ns shorturl.env
  (:require [clojure.edn :refer [read-string]]))


(def ^:private env-variables(read-string (slurp "env.edn")))

(defn env
  "Take key out of env.edn. If key is not preset look up at system environment.
   If not found neither, return nil."
  [k]
  (or (k env-variables) (System/getenv (name k))))
