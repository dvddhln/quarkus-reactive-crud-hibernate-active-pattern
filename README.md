# reactive-crud-hibernate-active-pattern
This project demonstrates the active record pattern with quarkus, panache and the ORM hibernate provider 
within quarkus. It relies on a postgres sql database and presents a shopping cart with products.

This project uses Quarkus, the Supersonic Subatomic Java Framework.


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
    
### Example response
    {
      "cartItems": [
        {
          "product": {
            "createdAt": "2021-05-31T06:07:16Z",
            "description": "description",
            "id": 4,
            "title": "Product4",
            "updatedAt": "2021-05-31T06:07:16Z"
          },
          "quantity": 1
        },
        {
          "product": {
            "createdAt": "2021-05-31T06:07:16Z",
            "description": "description",
            "id": 3,
            "title": "Product3",
            "updatedAt": "2021-05-31T06:07:16Z"
          },
          "quantity": 8
        }
      ],
      "cartTotal": 9,
      "id": 1,
      "name": "MyCart"
    }