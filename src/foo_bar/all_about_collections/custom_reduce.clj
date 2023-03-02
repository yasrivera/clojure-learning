(ns foo-bar.all-about-collections.custom-reduce)

; ["Daniela" "Guilherme" "Carlos" "Paulo" "Lucia" "Ana"]

;; Without stoping criteria
#_(defn custom-counting
  [total-until-now elements]
  (recur (inc total-until-now) (rest elements)))

; (custom-counting 0 ["Daniela" "Guilherme" "Carlos" "Paulo" "Lucia" "Ana"])

;; Trying to deal with empty sequences
#_(defn custom-counting
  [total-until-now elements]
  (if (next elements)
   (recur (inc total-until-now) (rest elements))
    total-until-now))

; (custom-counting ["Daniela" "Guilherme" "Carlos" "Paulo" "Lucia" "Ana"])

(defn custom-counting
  ([elements]
   (custom-counting 0 elements))
  ([total-until-now elements]
  (if (seq elements)
    (recur (inc total-until-now) (rest elements))
    total-until-now)))

(custom-counting ["Daniela" "Guilherme" "Carlos" "Paulo" "Lucia" "Ana"])