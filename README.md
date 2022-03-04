# fixa

Better fixtures for Clojure/script

Why does `clojure.test` limit us to `:each` and `:once` fixtures for the entire namespace?
Shouldn't the granularity of an individual test be where we can apply fixtures?

## Usage

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

## License

Copyright © 2021 oliyh

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
