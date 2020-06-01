# fvm [![Clojars Project](https://img.shields.io/clojars/v/fvm-project/fvm.svg)](https://clojars.org/fvm-project/fvm)

**fvm** is a Clojure library for writing self-optimizing interpreters.

## How it works

fvm provides a function called `defnode` for defining instruction nodes with the following signature:

```clojure
(fvm/defnode <node-type> <opts> <handler-fn>)
```

As an example, Clojure's `def` could be implemented in the following manner:

```clojure
(fvm/defnode ::def {}
  (fn [state]
    (let [def-node (-> state ::fvm/nodes first)
          curr-ns (or (::curr-ns state) 'user)
          {::keys [name value]} def-node]
      (-> state
          (assoc-in [curr-ns ::vars name] value)
          (update ::fvm/nodes rest)))))
```

assuming that your parser had parsed the expression `(def a 1)` into the fvm node:

```clojure
{::fvm/type ::def
 ::name 'a
 ::value 1}
```

## Examples

[ednlang](https://github.com/fvm-project/ednlang) is a simple stack-based concatenative language implemented using fvm.

ednlang is meant to showcase all the features of fvm, and is also used to test and profile fvm.

Work is ongoing to implement a lisp on top of fvm.

## Status

This is **EXPERIMENTAL** software. Here be dragons.

## License

Copyright © 2020 Divyansh Prakash

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
