(ns store.aula3
  (:require [foo-bar.store.db :as s.db]))

(println (s.db/all-orders))
;;teste