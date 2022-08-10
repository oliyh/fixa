(ns fixa.kaocha
  (:require [fixa.core :refer [get-fixture]]))

(defn wrap-run [run]
  (fn [testable test-plan]
    (if-let [fixture (get-fixture (:kaocha.testable/meta testable))]
      (fixture #(run testable test-plan))
      (run testable test-plan))))
