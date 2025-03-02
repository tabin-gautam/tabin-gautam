# Integration with ChatGpt Demo & Banking API-Dockerized(Monolithic Application)
### Pending Decoupled API(Microservice) coming soon......stay tuned!!!
### Overview
Demo of integrating spring boot application with chatgpt api.
The Banking API provides endpoints to perform basic banking operations such as account creation, money transfers, balance retrieval, and viewing transaction history. This API is built using Java Spring Boot and provides a set of RESTful web services for handling banking transactions.

### Features
Faq: Returns the response from chatgpt when user ask questions.<br>
Account Management: Create a new account and manage user details.<br>
Money Transfer: Transfer money between two accounts.<br>
Transaction History: View transaction history of a given account.<br>
Balance Retrieval: Get the balance of a specific account.

### Technologies
Java 17,
Spring Boot 3.4.2,
Spring MVC,
JUnit for Unit Testing,
MockMvc for API Testing,
H2 Database (In-memory).
### Docker image 
https://hub.docker.com/r/tabinrajgautam/banking-api  or<br>
docker pull tabinrajgautam/banking-api
### Run Docker Application Command
docker build -t banking-api .<br>
docker tag banking-api tabinrajgautam/banking-api:1.0<br>
docker push tabinrajgautam/banking-api:1.0<br>
docker run -p 8080:8080 -t tabinrajgautam/banking-api:1.0<br>

