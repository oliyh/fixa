(ns fixa.kaocha.fail-after-test
  (:require [clojure.test :refer [is deftest] :as test]
            [fixa.fixtures :refer [fail-after-expectation *failed-after?*]]))

(deftest ^{:fixa/fail-after "2022-07-10"
           :fixa/fixture fail-after-expectation}
  fail-after-past-test
  (is @*failed-after?*))

(deftest ^{:fixa/fail-after (str (java.time.LocalDate/now))
           :fixa/fixture fail-after-expectation}
  fail-after-today-test
  (is @*failed-after?*))

(deftest ^{:fixa/fail-after (str (.plusDays (java.time.LocalDate/now) 1))
           :fixa/fixture fail-after-expectation}
  fail-after-future-test
  (is (not @*failed-after?*)))
