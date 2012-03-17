(ns snippets.hsqldb-database-connection)

(def ^:dynamic db
     {:classname "org.hsqldb.jdbcDriver"
      :subprotocol "hsqldb"
      :subname "/tmp/snippets.db"})

