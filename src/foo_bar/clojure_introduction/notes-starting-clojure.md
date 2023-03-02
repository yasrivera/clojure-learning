# Starting with the classic
```clojure
(println "Hello world")
```
First lesson: `println` returns `nil` value by default. So when setting a REPL server and evaluating it, probably `nil` will be there after the main message. This actually leads to another important point characteristic of clojure: **every expression returns a value.** `if` expression returns `nil` value if `else` branch is not defined, for example. Fortunatelly, there's a function that can be used (after being imported) to print more complex data structures (such as mapsin a prettier way:
```clojure
(pprint "Hello World")
```
<br/>

# Symbols and functions
**Immutability** is an important characteristic of functional programming, so variable is not a thing, right? Here, we create **symbols** and **functions**. Symbols are created using `def` and functions, `defn`. Clojure documentation has an interesting explanation for this nomenclature:

> _It might be useful to think of `defn` as a contraction of `def` and `fn`. The `fn` defines the function and the `def` binds it to a name._

Below there's a really good example that illustrate that from the documentation itself:

```clojure
(defn greet [name] (str "Hello, " name))

(def greet (fn [name] (str "Hello, " name)))
```

<br/>

## Creating functions

```clojure
(defn print-message []
  (println "Printing this beautiful message *-*"))
```

```clojure
(defn greetings
  "Says Hello, Name"
  [name]
  (println "Hello" name))
```

- Creating Multi-arity functions (example from documentation)
  
```clojure
(defn messenger
  ([] (messenger "Hello World"))
  ([msg] (println msg)))
```

```clojure
user=> (messenger)
Hello World!
nil

user=> (messenger "Message example")
Message example
nil
```
- Creating Variadic functions
  
```clojure
(defn hello 
  [greeting & who]
  (println greeting who))
```

```clojure
(hello "Hello" "Yasmin" "from" "Brazil")
```
In the example above, `who` represents a list with collected paramaters from the function calling.

- Creating symbols without creating memory garbage:

```clojure
(defn apply-discount
  [total]
  (let [discount 0.1] 
    (* total (- 1 0.1))))
```

Using `let` to create symbols with local scope instead of creating a symbol with `global` scope as shown below:

```clojure
(defn apply-discount
  [total]
  (def discount 0.1)
  (* total (- 1 0.1)))
```

- Using `#()` to create an anonymous function

```clojure
(#(+ % 5) 3)
```

- Using `partial` keyword

```clojure
((partial + 5) 3)
```

<br/>

## What are pure functions?
We say that we have a pure function when, given an input, no matter how many times I call the same function for this input, there will be the same output. Below, there are two examples of pure functions.

```clojure
(defn apply-discount 
  "Apply a 20% discount to a given value"
  [total]
  (* total 0.2))
```

```clojure
(defn apply-discount
  [total]
  (* total (- 1 0.1)))
```

<br/>

## Functions as arguments

In Clojure, functions are [first-class citizens](https://clojure.org/guides/higher_order_functions). This means they can be treated as values too. Functions that receive and returns other functions are called **Higher Order Functions**.

```clojure
(defn evaluate
  [item-of-evaluation]
  (if (= (rem item-of-evaluation 2) 0)
    "Even"
    "Odd"))
```

```clojure
(defn do-math-and-evaluate
  [apply-some-function? given-number]
  (if (= "Even" (apply-some-function? given-number))
    (println (+ given-number 10))
    (println (- given-number 10))))
```

- Passing a function as a parameter of another function
  
```clojure
(do-math-and-evaluate evaluate 101)
```

- Passing a lambda function (anonymous function) defined with 'fn' keyword to not refer a symbol to it
  
```clojure
(do-math-and-evaluate (fn [value] (> 100 value)) 1000)
```

- Shorthand for passing a lambda function (anonymous function) with `%`

```clojure
(do-math-and-evaluate #(< % 100) 10)
```

<br/>

# How-to conditionals

```clojure
(if (< 500 100)
  (println true) ;; in affirmative case
  (println false)) ;; else
```

If we don't set an 'else' condition, it will returns `'nil'` because of what was discussed earlier: every expression returns a default value in clojure, `'nil'` is commonly this value.

```clojure
(if nil 
  "true"
  "false")
```

Something interesting to add here is that `'if'` here is actually what we call a `form`, not a function. To understand more about it, just see documentation [here](https://clojure.org/reference/special_forms#if).

<br/>

# Collections and methods

```clojure
(def first-vector ["A", "B", "C", "D"])
```

To print a vector, you can simply write it's name. If you write `(first-vector)` this would throw an error since first-vector is not a function.

```
(first-vector 0)

(first-vector 2)

(first-vector 4)
```

Before seeing some methods that can be used with vectors, it's important to highlight immutability influence throughout a functional programming language. **Every method seen below are not going to change respective vectors used as their parameters.** They are going to create a temporary copy of them in runtime and apply some code in it.

- Counting how many elements there are in the vector with `count` function:

```clojure
(count first-vector)
```

- Returning a new collection of items with a given element added with `conj` function:

```clojure
(conj first-vector "F")
```

- Adding "F" before a collection with `conj` function and `'`:
  
```clojure
(conj '("G", "H") "F")
```

<br/>

> `conj` function can not only be used by vectors but by lists, sets and maps too. When operating on a set, the conj function returns a new set with one or more keys "added"; on a list, returns a new list with one or more items "added" to the front; on a map, returns a new map with one or more key-value pairs "added"; and as we saw, on a vector, returns a new vector with one or more items "added" to the end.

<br/>

- Using `get` function to access some position in a vector.
  
```clojure
(def prices [30 700 1000])
```

```clojure
user=> (get prices 1)
30
```

This is the best way to access a position in a vector because it has built-in error treatment, avoiding possible exceptions being throwned by accesing directly a position like done below:

```clojure
(prices 0)
```

Instead of throwing an `IndexOutOfBound` error, it returns `nil`. And it's possible to define a default value to be returned in this case by adding it as the fourth argument.

```clojure
(get prices 15 "nao existe")
```

- Incrementing a value using `inc` function:

```clojure
user=> (inc 5)
6
```

- Decrementing a value using `dec` function:

```clojure
user=> (dec 5)
4
```

- Using 'update' function to update a value in a vector (won't change the original one):

```clojure
(update prices 0 inc)
```

<br/>

# `map`, `filter` and `reduce`

First, creating a simple function to square numbers to use as example:

```clojure
(defn square-value
  [value]
  (* value value))
```

- Calling 'map' function to square each element of 'prices' vector

```clojure
(println (map square-value prices))
```

- Using 'range' function to generate a sequence of numbers with the given length.

```clojure
(range 10) 
```

- Calling 'filter' function to find even numbers in a sequence

```clojure
(filter even? (range 10))
```

- Calling 'reduce' function to return the sum of prices's vector elements.

```clojure
(reduce + prices)
```

<br/>

# Hashmaps

Creating a hashmap of first level and strings as keys:

```clojure
(def hash-little-map {"First" 1,
                      "Second" 2,
                      "Third" 3,
                      "Fourth" 4,
                      "Fifth" 5,
                      "Sixth" 6})
```

- Using `count` function to count how many elements there are in a given map:

```clojure
(count hash-little-map)
```

- Using `keys` function to return all keys of a given map:

```clojure
(keys hash-little-map)
```

- Using `vals` function to return all values of a given map:

```clojure
(vals hash-little-map)
```

<br/>

Creating another hashmap with keywords as keys:

```clojure
(def hash-keywords-map {:first 1,
                        :second 2,
                        :third 3,
                        :fourth 4,
                        :fifth 5,
                        :sixth 6})
```

- Using `assoc` function to associate a value to a key (or create the given pair) to a given map:

```clojure
(assoc hash-keywords-map :seventh 7)
```

- Using `dissoc` function to dissociate a key from a map:
  
```clojure
(dissoc hash-keywords-map :sixth)
```

- Using `update` to update a given key with a value or apply a function to it's value:

```clojure
(update hash-keywords-map :first inc)
```

Below there are three ways to access items in a hashmap. The first is rarely used since it can throw a `NullPointerException` error if the map is `nil`. The other two are best and the second is more frequently used.

```clojure
(hash-second-level-map :first)
```

```clojure
(:first hash-second-level-map)
```

```clojure
(get hash-second-level-map :first)
```

<br/>

Creating a hashmap of second level:

```clojure
(def hash-second-level-map{
                           :first {:letter "A", :number 1}
                           :second {:letter "B", :number 2}
                           :third {:letter "C", :number 3}
                           :fourth {:letter "D", :number 4}
                           :fifth {:letter "E", :number 5}
                           :sixth {:letter "F", :number 6}})
```

If a hashmap has more than one level and we want to modify a value in an inner point, it's necessary to use 'update-in' function to update it.

```clojure
(update-in hash-second-level-map [:first :number] inc)
```

<br/>

# Threading

## Threading First

```clojure
(println (-> hash-second-level-map
             :first
             :letter))
```

This has the same result than:

```clojure
(println (:letter (:first hash-second-level-map)))
```

But is more readable.

<br/>

## Threading last

```clojure
(defn get-numbers [[_key value]]
  (:number value))
```

```clojure
(defn total-numbers
  [hash-second-level-map]
  (->> hash-second-level-map ; notice two arrows to symbolize threading last
       (map get-numbers ,,,)
       (reduce + ,,,)))
```

```clojure
user=> (total-numbers hash-second-level-map) 
21
```

> **OBS:** `,,,` represents the given collection.

<br/>

A more polite way to do it would be:

```clojure
(defn get-numbers [hash-item]
  (:number hash-item))
```

```clojure
(defn total-numbers
  [hash-second-level-map]
  (->> hash-second-level-map
       vals
       (map get-numbers ,,,)
       (reduce + ,,,)))
```

```clojure
user=> (total-numbers hash-second-level-map) 
21
```

<br/>

# Destruct map entries

```clojure
(defn destructing-map-entries
  [[key value]]
  (println key "<and>" value))

(map destructing-map-entries hash-second-level-map)
```

<br/>

# Tips about REPL and Clojure setup

- When changing a function in a .clj file, it's necessary to reload the namespace for the changes to take effect. I was restarting the REPL server till I found this command:

```clojure
(use 'your.namespace :reload)
```