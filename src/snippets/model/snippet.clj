(ns snippets.model.snippet
  (:use snippets.mysql-database-connection)
  (:require clj-record.boot))

(clj-record.core/init-model
 :table-name "snippets")

