# clj-stream-stats

Accumulators for calculating some basic statistics: means, variance and standard deviation.

```clojure
(def a (accum-mean))
(a 1 2 3 4 5)
; Produces 3
```

Please note this is extremely work-in-progress, although any suggestions would be extremely welcomed.


## License

Copyright &copy; 2012 Paul Ingles

Distributed under the Eclipse Public License, the same as Clojure.
