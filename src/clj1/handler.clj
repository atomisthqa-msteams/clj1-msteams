(ns clj1.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(defn get-pizza []
  {:pizza :pie
   :with :mushrooms
   :requires :mozzarella
   :wrong :pineapple
   :and :onions})

(defn get1 [] 1)
(defn get2 [] 2)

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Clj1"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]}}}

   (context "/api" []
     :tags ["api"]

     (GET "/plus" []
       :return {:result Long}
       :query-params [x :- Long, y :- Long]
       :summary "adds two numbers together"
       (ok {:result (+ x y)}))

     (POST "/echo" []
       :return Pizza
       :body [pizza Pizza]
       :summary "echoes a Pizza"
       (ok (get-pizza))))))
