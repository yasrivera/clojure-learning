(ns foo-bar.all-about-collections.aula1)

(defn my-map
  [function sequence]
  (let [first (first sequence)]
    (if (not (nil? first))
      (do
        (function first)
        (my-map function (rest sequence))))))

(my-map println ["Dani", "Gui", "Carlos", "Paulo", "Lucia", "Ana"])