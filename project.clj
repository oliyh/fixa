(defproject fixa "0.1.0-SNAPSHOT"
  :description "Better fixtures for Clojure tests"
  :url "https://github.com/oliyh/fixa"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns fixa.core}
  :profiles {:dev {:dependencies [[lambdaisland/kaocha "1.63.998"]]}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]
            "test-kaocha" "kaocha"
            "test-clj-test" ["test" ":only" "fixa.clj-test-test"]
            "test" ["do" ["test-kaocha"] ["test-clj-test"]]})
