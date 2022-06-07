# jcip

Download code examples for the book [Java Concurrency in Practice](https://jcip.net/).

## Installation

Download from https://github.com/tangarts/jcip

```
git clone  https://github.com/tangarts/jcip
```

## Usage

To get the code examples for the book Java Concurrency in Practice run the project directly via `:exec-fn`:

    $ clojure -X:run

this will download the code in the `resources/` directory.

Run the project's tests:

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar:

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run the uberjar with:

    $ java -jar target/jcip-0.1.0-SNAPSHOT.jar

If you remove `version` from `build.clj`, the uberjar will become `target/jcip-standalone.jar`.

## License

Copyright © 2022 tangarts

Distributed under the Eclipse Public License version 1.0.

## TODO

- [ ] Tests :)
