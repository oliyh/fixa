(ns fixa.core
  (:require [clojure.test :refer [join-fixtures is]])
  #?(:clj (:import [java.time LocalDate])))

(defn- today []
  #?(:clj (LocalDate/now)
     :cljs (js/Date.)))

(defn- is-after? [now then]
  (println "is-after?" now then)
  #?(:cljs (println (< now then)))
  #?(:clj (.isAfter now then)
     ;; todo fix this
     :cljs (< now then)))

(defn- parse-date [date]
  (println "parse-date" date)
  #?(:clj (LocalDate/parse date)
     ;; todo is this right?
     :cljs (js/Date. date)))

(defn- run-after-fixture [date]
  (fn [f]
    (if (is-after? (today) (parse-date date))
      (f)
      (do (is :fixa.run-after/skipped)
          nil))))

(defn- fail-after-fixture [date]
  (fn [f]
    (is (is-after? (parse-date date) (today))
        (str "Fail after" date))
    (f)))

(defn get-fixture [meta]
  (when meta (println "kaocha hook" meta))
  (when-let [fixtures (seq (reduce into []
                                   [(when-let [run-after (:fixa/run-after meta)]
                                      [(run-after-fixture run-after)])
                                    (or (:fixa/fixtures meta)
                                        (when-let [fixture (:fixa/fixture meta)]
                                          (println "== fixture is " (type fixture))
                                          [fixture]))
                                    (when-let [fail-after (:fixa/fail-after meta)]
                                      [(fail-after-fixture fail-after)])]))]
    (println "found fixtures" fixtures)
    (join-fixtures fixtures)))
