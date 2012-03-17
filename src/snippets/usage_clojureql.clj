(ns snippets.usage-clojureql
  (:refer-clojure :exclude [take drop sort distinct compile conj! disj! case])
  (:use clojure.contrib.sql clojureql.core)
  (:use snippets.mysql-database-connection))

(def snippets (table db :snippets))

(defn select-snippets []
  @snippets)

(defn select-snippet [id]
  (first @(select snippets (where (= :id id)))))

(comment
 (defn now [] (java.sql.Timestamp. (.getTime (java.util.Date.))))

 (defn insert-snippet [body]
   (:id (last @(conj! snippets {:body body :created_at (now)}))))
 )

;; automatic timestamp in mySql
(defn insert-snippet [body]
  (:id (last @(conj! snippets {:body body}))))
