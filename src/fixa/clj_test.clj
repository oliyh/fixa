(ns fixa.clj-test
  (:require [clojure.test :as ct]
            [fixa.core :refer [get-fixture]]))

(defn with-fixtures [meta f]
  (fn []
    (if-let [fixture (get-fixture meta)]
      (fixture f)
      (f))))

;; plan was to have a single fixture that would pick up the fixture metadata from the function being run / metadata of the var being tested
;; neither is possible, so this is obsolete
#_(defn fixa-fixture [f]
  (println "testing vars" ct/*testing-vars*)
  (println "testing vars" (meta (last ct/*testing-vars*)))
  (let [fixtures (or (::fixtures (meta ct/*testing-vars*))
                     (when-let [fixture (::fixture (meta f))]
                       [fixture])
                     [])
        test-fixture (join-fixtures fixtures)]
    (println "Found fixtures" (count fixtures) fixtures)
    (test-fixture f)))

;; don't actually need this at least in clojure.test
;; because the testing function is repeatedly wrapped with anonymous functions that don't propagate meta
;; also the fixtures are run before *testing-vars* is bound so cannot access the meta of the var being tested either
;; so you can't get the meta off the var or the fn and it's all a dead end
#_(defn install! []
  (println "Installing fixtures in" *ns*)
  (use-fixtures :once fixa-fixture))

;; have to monkey patch deftest to smuggle the fixtures through, at least for clojure.test
(defmacro deftest [name & body]
  `(def ~(vary-meta name assoc :test `(with-fixtures ~(meta name) (fn [] ~@body)))
     (fn [] (ct/test-var (var ~name)))))
