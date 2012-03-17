(defproject snippets "1.0.0-SNAPSHOT"
  :description "save code snippets to a simple memory database"
  :dependencies
  [[org.clojure/clojure "1.3.0"]
   ;;[org.clojure.contrib/standalone "1.3.0-alpha4"]
   ;;[org.clojure.contrib/NAME-OF-THE-LIBRARY "1.3.0-alpha2"]
   [org.clojure.contrib/sql "1.3.0-alpha4"]
   [org.clojure.contrib/repl-utils "1.3.0-alpha4"]
   
   [ring/ring-core "1.1.0-SNAPSHOT"]
   [ring/ring-devel "1.1.0-SNAPSHOT"]
   [ring/ring-servlet "1.1.0-SNAPSHOT"]
   [ring/ring-jetty-adapter "1.1.0-SNAPSHOT"]
   [compojure "1.0.1"]
   [hiccup "1.0.0-beta1"]

   [clojureql "1.0.3"]
   [hsqldb "1.8.0.10"];"2.0.0"
   [clj-record "1.1.1"]
   [mysql/mysql-connector-java  "5.1.6"]
   ]
  
  :dev-dependencies
  [[lein-run "1.0.1-SNAPSHOT"]
   [swank-clojure "1.3.4" :exclusions [org.clojure/clojure]]]
  :jvm-opts ["-Xmx64M"]
  :main run)
