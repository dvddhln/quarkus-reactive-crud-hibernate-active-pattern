# reactive-crud-hibernate-active-pattern
This project demonstrates the active record pattern with quarkus, panache and the ORM hibernate provider 
within quarkus. It relies on a postgres sql database and presents a shopping cart with products.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

It demonstrates lazy fetch association patterns with the mutiny fetch, ofcourse this isn’t necessary if you fetch the association eagerly.

This project is presented in the following article


[Creating a CRUD shopping service with Quarkus, Hibernate Reactive ORM Panache and PostgreSQL using Active Record Pattern](https://dvddhln.medium.com/creating-a-crud-shopping-service-with-quarkus-hibernate-orm-panache-and-postgresql-using-active-41a755693f12)

###  Run Postgres docker container

     docker run -d --rm --name my_reative_db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=my_db -p 5432:5432 postgres:10.5

### Create shopping cart

     curl-s -X POST "http://localhost:8080/v1/carts" -H "Content-Type: application/json" -d '{"name":"myCart"}'

### Add a product

     curl -i -X POST "http://localhost:8080/v1/products" -H "Content-Type: application/json" -d '{"title":"product_title","description":"product_description"}'

### Get all products back

     curl -i -X GET "http://localhost:8080/v1/products" 
     
### Add product to cart    
     
     curl -s -X PUT "http://localhost:8080/v1/carts/1/1"
     
### Delete product from cart

     curl -s -X DELETE "http://localhost:8080/v1/carts/1/1"

### Get all carts 

    curl -i -X GET "http://localhost:8080/v1/carts" 
    
### Get single cart 

    curl -i -X GET "http://localhost:8080/v1/carts/1" 

### Build Docker Image

     mvn clean install -Dquarkus.container-image.build=true -Dmaven.test.skip

### Run the Application
    
    docker run --net=host -it --rm --name tic_toc  -p 8080:8080 <name>/reactive-rest-hibernate:1.1.0
    
### Clean DB

    docker rm $(docker ps -a -q) -f

    docker volume prune
    
### CRUD Product Page

Located at http://localhost:8080

### Native image build

    ./mvnw package -Pnative -Dquarkus.native.container-build=true
    
### Example response GET All Carts
    [
      {
        "cartItems": [
          {
            "product": {
              "createdAt": "2021-05-31T06:11:14Z",
              "description": "description",
              "id": 2,
              "title": "Product2",
              "updatedAt": "2021-05-31T06:11:14Z"
            },
            "quantity": 1
          },
          {
            "product": {
              "createdAt": "2021-05-31T06:11:14Z",
              "description": "description",
              "id": 1,
              "title": "Product1",
              "updatedAt": "2021-05-31T06:11:14Z"
            },
            "quantity": 2
          }
        ],
        "cartTotal": 3,
        "id": 1,
        "name": "MyCart"
      },
      {
        "cartItems": [
          {
            "product": {
              "createdAt": "2021-05-31T06:11:14Z",
              "description": "description",
              "id": 2,
              "title": "Product2",
              "updatedAt": "2021-05-31T06:11:14Z"
            },
            "quantity": 1
          },
          {
            "product": {
              "createdAt": "2021-05-31T06:11:14Z",
              "description": "description",
              "id": 1,
              "title": "Product1",
              "updatedAt": "2021-05-31T06:11:14Z"
            },
            "quantity": 2
          }
        ],
        "cartTotal": 3,
        "id": 1,
        "name": "MyCart"
      },
      {
        "cartItems": [],
        "cartTotal": 0,
        "id": 2,
        "name": "myCart"
      }
    ]
