(ns snippets.view
  (:use clojure.contrib.sql
	[compojure.core]
	[hiccup.core]
	[hiccup.page-helpers]
	[hiccup.form-helpers]
	[ring.adapter.jetty]
	[ring.util.response]
	[snippets.snippet])
  (:use ring.middleware.file)
  (:use ring.middleware.file-info)
  (:require [compojure.route :as route]))

(defn create-snippet [body]
  (if-let [id (insert-snippet body)]
    (redirect (str "/" id))
    (redirect "/")))

(defn layout [title & body]
  (html
   [:head
    [:title title]
    (include-js "/public/javascripts/code-highlighter.js"
		"/public/javascripts/clojure.js")
    (include-css "/public/stylesheets/code-highlighter.css")]
   [:body
    [:h2 title]
    body]))

(defn new-snippet []
  (layout "Create a Snippet"
   (form-to [:post "/"]
     #_(text-area {:rows 20 :cols 73} "body")
     (text-area :body)
     [:br]
     (submit-button "Save"))))

(defn show-snippet [id]
  (layout (str "Snippet " id)
    (let [snippet (select-snippet id)]
      (html
       [:div [:pre [:code.clojure (:body snippet)]]]
       [:div (:created_at snippet)]))))

(defroutes snippet-app
  "Create and view snippets."
  (GET "/ping" [] "pong")
  (GET "/pong" [] "ping")
  (GET "/foo/:id" [id] (str "foo" id))
  (GET "/" [] (new-snippet))
  (GET "/:id" [id] (show-snippet id))
  ;;unknown id's will result in error
  (GET ["/public/:filename" :filename #".*"] [filename]
    (file-response filename {:root "./public"}))
  #_(GET "/public/*" [params]
    (or (serve-file (params :*)) :next))
  (POST "/" [params] (create-snippet (:body params)))
  (route/files "/" {:root "public"})
  (ANY "*" [] (route/not-found "Page not found"))
  (route/not-found "Page not found")
  )

#_(defserver snippet-server {:port 8080} "/*" (servlet snippet-app))
#_(def snippet-server (http-server {:port 8080} "/*" (servlet snippet-app)))

#_(defroutes main-routes
  (GET "/"  [] (workbench))
  (POST "/save" {form-params :form-params} (str form-params))
  (GET "/test" [& more] (str "<pre>" more "</pre>"))
  (GET ["/:filename" :filename #".*"] [filename]
    (response/file-response filename {:root "./static"}))
  (ANY "*"  [] "<h1>Page not found.</h1>"))

(future (run-jetty (var snippet-app) {:port 8080}))

#_(start snippet-server)
;; (stop snippet-server)