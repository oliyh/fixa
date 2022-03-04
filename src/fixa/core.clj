(ns fixa.core
  (:require [clojure.test :refer [join-fixtures]]))

(defn get-fixture [meta]
  (when-let [fixtures (or (:fixa/fixtures meta)
                          (when-let [fixture (:fixa/fixture meta)]
                            [fixture]))]
    (join-fixtures fixtures)))
