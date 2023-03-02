(ns foo-bar.all-about-collections.loop-in-clojure)

;; 'recur' here is related to the loop function
(defn custom-counting
  [elements]
  (loop [total-until-now 0
         remaining-elements elements]
    (if (seq remaining-elements)
      (recur (inc total-until-now) (next remaining-elements))
      total-until-now)))

(custom-counting ["Daniela" "Guilherme" "Carlos" "Paulo" "Lucia" "Ana"])

(println "teste")

;; 'loop', in general, seems like a code smell once it feels like flow control that could be isolated in a function.

;; 'for' in clojure: https://clojuredocs.org/clojure.core/for