(ns foo-bar.divideLater)

;;;; STARTING WITH THE CLASSIC
(println "Hello World")

; OBS.1: 'def' keyword is used to define symbols
(def first-number 10)
(println first-number)

; OBS.2: order of execution of Clojure functions is left to right
(+ 13 3)
(+ first-number 10)
(+ first-number (- first-number 3))

;;;; CREATING A VECTOR
(def first-vector ["A", "B", "C", "D"])

;; To print a vector, you can simply write it's name. If you write (first-vector) this would throw an error since first-vector is not a function.
(first-vector 0)
(first-vector 2)
(first-vector 4)

;; Counting how many elements there are in the vector
(count first-vector)

;; conj function returns a new collection of items with the given element added, but doesn't change the original collection
(conj first-vector "F")

;; Adding "F" before the given collection
(conj '("G", "H") "F")

; OBS.3: ',' is considered a blank space

;;;; CREATING FUNCTIONS WITH A SYMBOL ()
(defn print-message []
  (println "Printing this beautiful message *-*"))

(defn greetings
  "Says Hello, Name"
  [name]
  (println "Hello" name))

;;; Creating Multi-arity functions (example from documentation)
(defn messenger
  ([] (messenger "Hello World")) ;; calls 'messenger' function again with a parameter
  ([msg] (println msg)))

(messenger)
(messenger "Message example")

;;; Creating Variadic functions (example from documentation)
(defn hello 
  [greeting & who] ;; 'who' represents a list of collected parameters
  (println greeting who))

(hello "Hello" "Yasmin" "from" "Brazil")

;; 'defn' is a contraction of 'def' and 'fn': 'fn' defines the function and 'def' binds it to a name.

(defn greet 
  [name] 
  (str "Hello, " name))

#_{:clj-kondo/ignore [:redefined-var]}
(def greet (fn [name] (str "Hello, " name)))

;;;; PURE FUNCTIONS: given an input, no matter how many times I call the same function for this input, there will be the same output.
#_(defn apply-discount 
  "Apply a 20% discount to a given value"
  [total]
  (* total 0.2))

#_(defn apply-discount
  [total]
  (* total (- 1 0.1)))

;; Creates a garbage in the memory, DON'T DO IT
#_(defn apply-discount
  [total]
  (def discount 0.1) ;;Here
  (* total (- 1 0.1)))

;; Do this instead
#_(defn apply-discount
  [total]
  (let [discount 0.1] 
    (* total (- 1 0.1))))

; OBS.4: BigInt and BigDecimal types
(class 90N)
(class 90M)

;;;; HOW-TO CONDITIONALS
(if (< 500 100)
  (println true) ;; in affirmative case
  (println false)) ;; else

;; If we don't set an 'else' condition, it will returns 'nil' because it means 'false':
(if nil 
  "true"
  "false") ;; else

; OBS.5: 'if' here is a form, not a function: https://clojure.org/reference/special_forms#if

;;;; FUNCTIONS AS ARGUMENTS
(defn evaluate
  [item-of-evaluation]
  (if (= (rem item-of-evaluation 2) 0)
    "Even"
    "Odd"))

(defn do-math-and-evaluate
  [apply-some-function? given-number]
  (if (= "Even" (apply-some-function? given-number))
    (println (+ given-number 10))
    (println (- given-number 10))))

;; Passing a function as a parameter of another function
(do-math-and-evaluate evaluate 101)

;; Passing a lambda function (anonymous function) defined with 'fn' keyword to not refer a symbol to it
(do-math-and-evaluate (fn [value] (> 100 value)) 1000)

;; Shorthand for passing a lambda function (anonymous function) with % 
(do-math-and-evaluate #(< % 100) 10)

; OBS.6: When changing a function in a .clj file, it's necessary to reload the namespace for the changes to take effect. I was restarting the REPL server till I found this command:
#_(use 'your.namespace :reload)

; OBS.7: Functions are 'first class citizens' in Clojure.

; OBS.8: Functions that receive or return other functions are called 'higher order functions'.

;; The function above receives x and verifies if it's scrictly positive:  
(defn scrictly-positive? [x] (> x 0))

;;;; VECTORS, 'get' AND 'update'

;;; 'get' function: the best way to access some position in a vector because it has error treatment, avoiding exceptions.
(def precos [30 700 1000])
(precos 0)
(get precos 1)

; OBS.9: 'get' function behave different than just calling the vector and printing the element in a certain position. Instead of throwing an 'IndexOutOfBound' error, it returns 'nil'. And it's possible to define a default value to be returned in this case, just add it in the fourth argument.

(get precos 15 "nao existe")

;;; 'inc' (increment) function: both functions above return '6'
(+ 5 1)
(inc 5)

;;; 'dec' (decrement) function: both functions above return '4'
(- 5 1)
(dec 5)

;;; 'update' function
(update precos 0 inc)

;; Creating a function to do exactly what 'inc' does and taking the same effect by passing it as argument to 'update' function
(defn soma-1
  [valor]
  (+ valor 1))

(update precos 0 soma-1)

;;;; 'map', 'filter' AND 'reduce' FUNCTIONS

;;; 'map' function

;; Creating a simple function to square numbers
(defn square-value
  [value]
  (* value value))

;; Calling 'map' function to square each element of 'precos' vector
(println (map square-value precos))

;;; 'range' function: the function above returns first 10 numbers (0 to 9)
(range 10) 

;;; Calling 'filter' function to find even numbers in a sequence
(filter even? (range 10))

;; Calling 'reduce' function to return the sum of precos's vector elements.
(reduce + precos)

;;;; HASHMAP 

;;; Creating a hashmap
(def hash-little-map {"First" 1,
                      "Second" 2,
                      "Third" 3,
                      "Fourth" 4,
                      "Fifth" 5,
                      "Sixth" 6})

;;; 'count' function
(count hash-little-map)

;;; 'keys' function
(keys hash-little-map)

;;; vals
(vals hash-little-map)

;;; Creating another hashmap with keywords as keys
(def hash-keywords-map {:first 1,
                        :second 2,
                        :third 3,
                        :fourth 4,
                        :fifth 5,
                        :sixth 6})

(println hash-keywords-map)

;;; 'assoc' and 'dissoc' functions
(assoc hash-keywords-map :seventh 7)
(dissoc hash-keywords-map :sixth)
(update hash-keywords-map :first inc)

;;; Creating another hashmap of second level
(def hash-second-level-map{
                           :first {:letter "A", :number 1}
                           :second {:letter "B", :number 2}
                           :third {:letter "C", :number 3}
                           :fourth {:letter "D", :number 4}
                           :fifth {:letter "E", :number 5}
                           :sixth {:letter "F", :number 6}})

;; Above there's three ways to access items in a hashmap. The first is rare since it can throw a NullPointerException error if the map is null. The other two are best and the second is more frequently used.
(hash-second-level-map :first)

(:first hash-second-level-map)

(get hash-second-level-map :first)

;; If a hashmap has more than one level and we want to modify a value in an inner point, it's necessary to use 'update-in' function to update it.
(update-in hash-second-level-map [:first :number] inc)

;;;; THREADING

; OBS.10: ',,,' symbolizes a collection.

;; Threading first:
(println (-> hash-second-level-map ; notice one arrow to symbolize threading first
             :first
             :letter))

;; This has the same result than:
(println (:letter (:first hash-second-level-map)))

;; Threading last:

(defn get-numbers [[_key value]]
  (:number value))

(defn total-numbers
  [hash-second-level-map]
  (->> hash-second-level-map ; notice two arrows to symbolize threading last
       (map get-numbers ,,,)
       (reduce + ,,,)))

;; A more 'polite' way to do it:
#_{:clj-kondo/ignore [:redefined-var]}
(defn get-numbers [hash-item]
  (:number hash-item))

#_{:clj-kondo/ignore [:redefined-var]}
(defn total-numbers
  [hash-second-level-map]
  (->> hash-second-level-map
       vals
       (map get-numbers ,,,)
       (reduce + ,,,)))

(total-numbers hash-second-level-map) 
; But it's better to understand.

;;;; DESTRUCT 
(defn destructing-map-entries
  [[key value]]
  (println key "<and>" value))

(map destructing-map-entries hash-second-level-map)