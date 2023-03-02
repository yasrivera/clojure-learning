(ns foo-bar.all-about-collections.db)

(def order1{:usuario 15
             :itens {:backpack {:id :backpack :quantidade 2 :preco-unitario 80}
                     :shirt {:id :shirt :quantidade 3 :preco-unitario 40}
                     :sneaker {:id :sneaker :quantidade 1}}})

(def order2 {:usuario 16
              :itens {:backpack {:id :backpack :quantidade 1 :preco-unitario 80}
                      :shirt {:id :shirt :quantidade 2 :preco-unitario 40}
                      :sneaker {:id :sneaker :quantidade 1}}})

(def order3 {:usuario 15
              :itens {:backpack {:id :backpack :quantidade 1 :preco-unitario 80}
                      :shirt {:id :shirt :quantidade 0 :preco-unitario 40}
                      :sneaker {:id :sneaker :quantidade 1}}})

(def order4 {:usuario 20
              :itens {:backpack {:id :backpack :quantidade 1 :preco-unitario 80}
                      :shirt {:id :shirt :quantidade 10 :preco-unitario 40}
                      :sneaker {:id :sneaker :quantidade 2}}})

(defn  all-orders []
  [order1, order2, order3, order4])