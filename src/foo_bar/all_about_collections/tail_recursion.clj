(ns foo-bar.all-about-collections.tail-recursion)

(defn my-map
  [function sequence]
  (let [first (first sequence)]
    #_{:clj-kondo/ignore [:missing-else-branch]}
    (if (not (nil? first))
      (do
        (function first)
        (recur function (rest sequence))))))

(my-map println (range 100000))