# dbms-project

collab0rator :
muskan7898
samanwita-2003
Neeraj127
SymmetricEntropy
camperjett

# Commands to run:

### before making any changes:

* **git checkout <your-branch>**
* **git fetch --all**
* **git merge origin/main**

### after changes:

* **git add .**
* **git commit -m "_describe what changes you made_"**
* **git push -u origin <branch-name>**

then make a pull request...

## Connecting to MySQL

* create a new file in `backend/src/main/resources` named `application.properties` by copying `application.properties`
  already present in that folder
* change the fields `USERNAME`, `PASSWORD`, `NAME_OF_DATABASE`.

# Auth

        * Auth is done using signin api which checks if the user exists and matches creds.
        then the frontend stores the response and keeps auth.
        * Logout is client side and nothing to do with server.
        * while authed, each server api that needs user details in request body.

# Endpoints Backend

## Use Case:

======> samanwita

* orders/status/0 shows all orders GET
* orders/status/1 all orders to be shipped
* orders/status/2 cancelled orders
* orders/status/3 delivered orders
* orders/search/{x}/status/{y} x: order search, y: filter by status
* orders/{order-id}/cancel cancel order by order-id POST
* orders/{order-id}/details details of each order

=====< samanwita

* auth/signin
* /register

======> neeraj

* wishlist/ shows all items
* wishlist/add/{product_id}
* wishlist/remove/{product_id}
  ======< neeraj

=======> kriti

* dashboard/profile shows profile
* dashboard/profile/edit change user info.
* dashboard/address list addresses
* dashboard/address/add
* dashboard/address/edit/{id}
* dashboard/address/remove/{id}
* dashboard/review show all my reviews
* dashboard/review/edit/{id}
* dashboard/review/remove/{id}
  ======< kriti

* /category list all category
* /category/:cid/products lists products. Request body has optional attributes: POST
    1. brand array --- halted bcos it requires brand entity for 2NF
    2. price_low and price_high
    3. rating_low and rating_high
    4. availability
    5. discount

===> samanwita

* /search lists products. r body has optional attributes:
    1. term - the term we search {must-have}
    2. category
    3. brand array
    4. price_low and price_high
    5. rating_low and rating_high (0.0 -> 5.0)
    6. availability 0/1
       ====< samawita

[//]: # (    7. discount)

====> muskan

* /products/{id} details about product with id, Product FAQs, Reviews
* /products/{id}/faq/add add question
* /products/{id}/faq/remove/{qid}
* /products/{id}/faq/answer/{qid} answer a question
* /products/{id}/review/add
* /products/{id}/review/{rid}/rate
* /products/{id}/review/{rid}/remove
* /faq/{id}/vote
* /review/{id}/vote
  ======< muskan

```json
  {
}
```

* /cart/add/{id}
* /cart/update/{id} change qty of item
* /cart/remove/{id}
* /cart get all cart items
* /order/place request body has list of items as:

```json
{
  "payment": 3,
  "address_id": 3290,
  "items": [
    {
      "product_id": 1232,
      "qty": 3
    }
  ]
}
```

* feedback/

# Admin APIs

### Admin api have prefix - /admin

## **Manage Users**

* /users

[//]: # (* /users/add)

* /users/remove

[//]: # (* /users/update)

* /shopkeepers -- list all approved shopkeepers
* /shopkeepers/tentative -- membership requests
* /shopkeepers/action -- approve/decline membership for shopkeeper based on docs

## Approve Orders

* /orders/
* /orders/action -- approve/decline
* /orders/delete

## Category

* /category
* /category/add
* /category/remove
* /category/update

# Products

* /products
* /products/add
* /products/remove
* /products/update

# Reviews

* /reviews -- with query params:
    1. product
    2. category

````json
{
  "id": 123912,
  "username": "jady",
  "add": [
    {
      "id": 13123,
      "full_add": "sdfsdf"
    },
    {
      "id": 13123,
      "full_add": "sdfsdf"
    },
    {
      "id": 13123,
      "full_add": "sdfsdf"
    }
  ]
}
````

# Issues we overcame

* CORS issue when I call simple get api from react using axios
  **Solution** `file: com.WholeSailor.demo/config/MyConfig.java`
* `String sql2 = "SELECT LAST_INSERT_ID()"`; gets you primary of last insertion

# Normalization

* 1NF : the relation is in first normal form if it does not contain any composite or multi-valued attribute.
* 2NF : A relation that is in First Normal Form and every non-primary-key attribute is fully functionally dependent on
  the primary key, then the relation is in Second Normal Form (2NF).
    - how we have achieved it? all primary keys are single attributed.
* 3NF : 