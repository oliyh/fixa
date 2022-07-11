(ns fixa.clj-test.fixtures-test
  (:require [clojure.test :refer [is] :as test]
            [fixa.clj-test :refer [deftest]]
            [fixa.fixtures :refer [fixture-a fixture-b *fixtures-run*]]))

(deftest ^{:fixa/fixture fixture-a} single-fixture-test
  (is (= #{:a} *fixtures-run*)))

(deftest ^{:fixa/fixtures [fixture-a fixture-b]} multiple-fixtures-test
  (is (= #{:a :b} *fixtures-run*)))
