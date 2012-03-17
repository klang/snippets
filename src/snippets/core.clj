(ns snippets.core
  (:use (compojure core)
	(hiccup core page form)
	(ring.middleware params file file-info reload stacktrace)
	(ring.util response)
	(snippets middleware))
  (:require [compojure.route :as route]
	    (snippets.model [snippet   :as snippet])))

(def production?
  (= "production" (get (System/getenv) "APP_ENV")))

(def development?
  (not production?))

(defn layout [title & body]
  (html
   [:head
    [:title title]
    (include-js "/js/code-highlighter.js" "/js/clojure.js")
    (include-css "/css/code-highlighter.css")]
   [:body
    [:h2 title]
    body]))

(defn new-snippet []
  (layout "Create a Snippet"
   (form-to [:post "/snippet"]
     (text-area :body)
     [:br]
     (submit-button "Save"))))

(defn show-snippet [id]
  (layout (str "Snippet " id)
    (let [snippet (snippet/get-record id)]
      (html
       [:div [:pre [:code.clojure (:body snippet)]]]
       [:div (:created_at snippet)]))))

(defn create-snippet [body]
  (if-let [id (:id (snippet/create {:body body}))]
    (redirect (str "/snippet/" id))
    (redirect "/snippet")))

(defn parse-input [a b]
  [(Integer/parseInt a) (Integer/parseInt b)])

(defroutes handler
  (GET "/snippet" [] (new-snippet))
  (GET "/snippet/:id" [id] (show-snippet id))
  (POST "/snippet" [body] (create-snippet body))
  (ANY "/*" [path] (redirect "/snippet")))

(def app
  (-> #'handler
    (wrap-utf)
    (wrap-file "public")
    (wrap-params)
    (wrap-file-info)
    (wrap-request-logging)
    (wrap-if development? wrap-reload '[snippets.middleware snippets.core])
    (wrap-bounce-favicon)
    (wrap-exception-logging)
    (wrap-if production?  wrap-failsafe)
    (wrap-if development? wrap-stacktrace)))
