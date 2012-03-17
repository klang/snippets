(ns run
  (:use ring.adapter.jetty)
  (:require snippets.core))

(let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (future (run-jetty #'snippets.core/app {:port port})))