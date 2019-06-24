# MyRetailProducts
Is a SpringBoot Microservice RESTful API

# Tecnologies/Libraries
  Java 8
  Cassandra 3.11.4
  Maven 4.0
  SpringBoot Version 2.1.4
  Swagger 2.7
  PowerMock 1.7.4
 
# Cassandra Database details
  database=myretail
  cassandra host=localhost

  To run the application following commands has to be run on Cassandra DB
    CREATE KEYSPACE myretail WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
    CREATE TABLE myretail.product(id int PRIMARY KEY, price decimal, currency text);
    INSERT INTO myretail.product (id, price, currency) VALUES(13860428, 56.78, 'USD');

### Build the application ###
mvn clean install

### Run the Application ###
java -jar target/product-1.0.jar
 
### Accessing Application
* PORT: 8080
* Context Path: /myretail

* Swagger URL: http://localhost:8080/myretail/swagger-ui.html

### Additional Details ###
* ProductServiceImpl - It has the actual logic to get product details based on provided id
* ProductValidator - It has the logic to validate inputs
* BaseExceptionHandler - It generates the meaningful error response
* ProductResource - SpringBoot Controller provides services
* JUnits for the corresponding classes, used Mock where required
* Slf4j for logging, used default log properties

### Assumptions Made ###
* Price changes will be requested on Price or Currencycode.
### Sample Payloads for calling api ###
  GET http://localhost:8080/myretail/products/13860428
  Response:
  {  
   "id":13860428,
   "name":"The Big Lebowski (Blu-ray)",
   "currentPrice":{  
      "value":45.678,
      "currencyCode":"USD"
    }
   }
   
   PUT http://localhost:8080/myretail/products/13860428
   Requst payload
   {
      "currencyCode": "USD",
      "value": 56.78
    }
    
    Response: 200 (HttpResponse)
