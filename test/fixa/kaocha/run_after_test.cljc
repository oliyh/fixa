(ns fixa.kaocha.run-after-test
  (:require [clojure.test :refer [is deftest use-fixtures] :as test]
            [fixa.fixtures :refer [run-after-expectation *ran-after-tests*]]))

(use-fixtures :once run-after-expectation)

(deftest ^{:fixa/run-after "2022-07-10"} run-after-past-test
  (swap! *ran-after-tests* conj :run-after-past-test)
  (is true))

(deftest ^{:fixa/run-after #?(:clj (str (java.time.LocalDate/now))
                              :cljs "2022-08-10")}
  run-after-today-test
  (swap! *ran-after-tests* conj :run-after-today-test)
  (is false "This should not have run"))

(deftest ^{:fixa/run-after #?(:clj (str (.plusDays (java.time.LocalDate/now) 1))
                              :cljs "2099-01-01")}
  run-after-future-test
  (swap! *ran-after-tests* conj :run-after-today-test)
  (is false "This should not have run"))
