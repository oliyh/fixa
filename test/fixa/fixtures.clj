(ns fixa.fixtures)

(def ^:dynamic *fixtures-run* #{})

(defn fixture-a [f]
  (binding [*fixtures-run* (conj *fixtures-run* :a)]
    (println "running fixture a")
    (f)))

(defn fixture-b [f]
  (binding [*fixtures-run* (conj *fixtures-run* :b)]
    (println "Running fixture b")
    (f)))
