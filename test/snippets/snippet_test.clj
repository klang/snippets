(ns snippets.snippet-test
  (:use [snippets.snippet] :reload-all)
  (:use [clojure.test]))

(deftest test-select-snippets
  (is (= 2 (count (select-snippets))))
  (is (= (:body (select-snippet 0)) "(println :boo)"))
  (is (= (:body (select-snippet 1)) "(defn foo [] 1)")))
