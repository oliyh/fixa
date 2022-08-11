(ns fixa.kaocha.fixtures-test
  (:require [clojure.test :refer [is deftest] :as test]
            [fixa.fixtures :refer [fixture-a fixture-b *fixtures-run*]]))

(deftest no-fixtures-test
  (println "==== no fixtures test"))

(deftest ^{:fixa/fixture fixture-a} single-fixture-test
  (println "====running single-fixture-test")
  (is (= #{:a} *fixtures-run*)))

(deftest ^{:fixa/fixtures [fixture-a fixture-b]} multiple-fixtures-test
  (println "====running multiple-fixtures-test")
  (is (= #{:a :b} *fixtures-run*))
  (is (= false true)))
