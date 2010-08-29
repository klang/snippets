(ns snippets.snippet
  (:use clojure.contrib.sql))

(defn create-snippets []
  (create-table :snippets
    [:id :int "IDENTITY" "PRIMARY KEY"]
    [:body :varchar "NOT NULL"]
    [:created_at :datetime]))

(def db {:classname "org.hsqldb.jdbcDriver"
         :subprotocol "hsqldb"
         :subname "/tmp/snippet.db"})

(try 
 (with-connection db (create-snippets)) 
 (catch Exception e 
   (println "database already exist")))

(defn drop-snippets []
  (try
   (drop-table :snippets)
   (catch Exception e)))

(defn now [] (java.sql.Timestamp. (.getTime (java.util.Date.))))

(defn insert-snippets []
  (let [timestamp (now)]
    (seq 
     (insert-values :snippets
      [:body :created_at]		     
      ["(println :boo)" timestamp]
      ["(defn foo [] 1)" timestamp]))))

(defn sample-snippets []
  (with-connection db
     (drop-snippets)
     (create-snippets)
     (insert-snippets)))

(defn reset-snippets []
  (with-connection db
     (drop-snippets)
     (create-snippets)))

(defn ensure-snippets-table-exists []
  (try
   (with-connection db (create-snippets))
   (catch Exception _)))
 
(defmulti coerce (fn [dest-class src-inst] [dest-class (class src-inst)]))
(defmethod coerce [Integer String] [_ inst] (Integer/parseInt inst))
(defmethod coerce :default [dest-cls obj] (cast dest-cls obj))

(defn select-snippets []
  (with-connection db
    (with-query-results res ["select * from snippets"] (doall res))))

(defn print-snippets [] 
  (apply println (select-snippets)))

(defn sql-query [q]
  (with-query-results res q (doall res)))

(defn select-snippet [id]
  (with-connection db
    (first (sql-query ["select * from snippets where id = ?" (coerce Integer id)]))))

(defn last-created-id 
  "Extract the last created id. Must be called in a transaction
   that performed an insert. Expects HSQLDB return structure of
   the form [{:@p0 id}]."
  []
  (first (vals (first (sql-query ["CALL IDENTITY()"])))))

(defn insert-snippet [body]
  (with-connection db
    (transaction
     (insert-values :snippets
       [:body :created_at]
       [body (now)])
     (last-created-id))))
