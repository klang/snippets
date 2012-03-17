(ns snippets.usage-clj-record
  (:require (snippets.model [snippet   :as snippet])))

(comment
  ;; just showing how to use clj-record to interact with the database
  ;; .. it's so simple
  (snippet/get-record 1)
  (snippet/update {:body "(println \"hello, world\")" :id 1})
  (:id (snippet/create {:body "hello there"}))
  )