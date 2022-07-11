(ns fixa.core
  (:require [clojure.test :refer [join-fixtures is]])
  (:import [java.time LocalDate]))

(defn- run-after-fixture [date]
  (fn [f]
    (if (.isAfter (LocalDate/now) (LocalDate/parse date))
      (f)
      (do (is :fixa.run-after/skipped)
          nil))))

(defn- fail-after-fixture [date]
  (fn [f]
    (is (.isAfter (LocalDate/parse date) (LocalDate/now))
        (str "Fail after" date))
    (f)))

(defn get-fixture [meta]
  (when-let [fixtures (reduce into []
                              [(when-let [run-after (:fixa/run-after meta)]
                                 [(run-after-fixture run-after)])
                               (or (:fixa/fixtures meta)
                                   (when-let [fixture (:fixa/fixture meta)]
                                     [fixture]))
                               (when-let [fail-after (:fixa/fail-after meta)]
                                 [(fail-after-fixture fail-after)])])]
    (join-fixtures fixtures)))
