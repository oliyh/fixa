(ns fixa.kaocha
  (:require [fixa.core :refer [get-fixture]]))

(defn wrap-run [run]
  (fn [testable test-plan]
    (if-let [fixture (get-fixture (:kaocha.testable/meta testable))]
      (do (println "running fixtures around test")
          (fixture #(run testable test-plan)))
      (do (println "no fixtures, just runing test")
          (run testable test-plan)))))
