# fixa

Better fixtures for Clojure/script

Why does `clojure.test` limit us to a single set of `:each` and `:once` fixtures for all tests in a namespace?

Shouldn't we have the granularity of being able to apply particular fixtures to an individual test?

fixa lets you define fixtures at the individual test level using a simple metadata notation.

## Usage

Firstly you need to add the fixa metadata to your test vars, then choose the appropriate instructions for your test runner (kaocha or clojure.test).

Note that the fixa fixtures will run _after_ any fixtures defined by `use-fixtures`.

### Annotate your tests

Add `:fixa/fixture` (singular) or `:fixa/fixtures` (a list) as metadata to your `deftest`:

```clj
(deftest ^{:fixa/fixture fixture-a} single-fixture-test
  (is (= 1 1)))

(deftest ^{:fixa/fixtures [fixture-a fixture-b]} multiple-fixtures-test
  (is (= 1 1)))
```

### Kaocha

All that's needed is the `fixa.kaocha/wrap-run` plugin. Add it to your `tests.edn` like this:

```clj
#kaocha/v1 {:plugins [:hooks]
            :kaocha.hooks/wrap-run [fixa.kaocha/wrap-run]}
```

### clojure.test

Unfortunately due to the way clojure.test is written, an alternative `deftest` is required to smuggle the fixtures through.

```clj
(require '[fixa.clj-test :refer [deftest]])

(deftest ^{:fixa/fixture fixture-a} single-fixture-test
  (is (= 1 1)))
```
