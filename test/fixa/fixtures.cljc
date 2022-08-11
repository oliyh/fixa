(ns fixa.fixtures
  (:require [clojure.test :as test]))

(def ^:dynamic *fixtures-run* #{})

(defn fixture-a [f]
  (println "running fixture a")
  (binding [*fixtures-run* (conj *fixtures-run* :a)]
    (f)))

(defn fixture-b [f]
  (println "Running fixture b")
  (binding [*fixtures-run* (conj *fixtures-run* :b)]
    (f)))

(def ^:dynamic *failed-after?* (atom false))

(defn fail-after-expectation
  "Intercept the fail-after test failure in the clojure.test reporting"
  [f]
  (let [dr test/do-report
        failed-after? (atom false)]
    (with-redefs [test/do-report (fn [m]
                                   (dr (if (= :fail (:type m))
                                         (do (reset! *failed-after?* true)
                                             (assoc m :type :pass))
                                         m)))]
      (binding [*failed-after?* failed-after?]
        (f)))))

(def ^:dynamic *ran-after-tests* (atom #{}))

#?(:clj (defn run-after-expectation
          "Ensure the run-after tests behaved properly"
          [f]
          (let [ran-after-tests (atom #{})]
            (binding [*ran-after-tests* ran-after-tests]
              (f)
              (test/is (= #{:run-after-past-test}
                          @ran-after-tests)))))
   :cljs (def run-after-expectation
           {:before (fn []
                      (reset! *ran-after-tests* #{}))
            :after (fn []
                     (test/is (= #{:run-after-past-test}
                                 @*ran-after-tests*)))}))
