(defproject snippets "1.0.0-SNAPSHOT"
  :description "save code snippets to a simple memory database"
  :dependencies
  [[org.clojure/clojure "1.2.0"]
   [org.clojure/clojure-contrib "1.2.0"]
   [ring/ring-core "0.3.11"]
   [ring/ring-devel "0.3.11"]
   [ring/ring-servlet "0.3.11"]
   [ring/ring-jetty-adapter "0.3.11"]
   [compojure "0.6.5"]
   [hiccup "0.3.6"]
   [clojureql "1.0.1"]
   [hsqldb "1.8.0.10"]] ;"2.0.0"
  :dev-dependencies
  [[lein-run "1.0.0-SNAPSHOT"]
   [swank-clojure "1.2.1"]]
  :jvm-opts ["-Xmx64M"])
