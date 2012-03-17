(ns snippets.mysql-database-connection)

(def ^:dynamic db
     {:classname   "com.mysql.jdbc.Driver"
      :subprotocol "mysql"
      :user        "snippets"
      :password    "snippets"
      :subname     "//localhost:3306/snippets"})
