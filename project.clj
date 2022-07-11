(defproject com.github.oliyh/fixa "0.1.0-SNAPSHOT"
  :description "Better fixtures for Clojure tests"
  :url "https://github.com/oliyh/fixa"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :repl-options {:init-ns fixa.core}
  :profiles {:provided {:dependencies [[org.clojure/clojure "1.10.3"]]}
             :dev {:dependencies [[lambdaisland/kaocha "1.63.998"]]}}
  :test-selectors {:default (fn [m & _]
                              (re-find #"^fixa\.clj-test" (str (:ns m))))}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]
            "test-kaocha" "kaocha"
            "test-clj-test" ["test" ":default"]
            "test-all" ["do" ["test-kaocha"] ["test-clj-test"]]})
