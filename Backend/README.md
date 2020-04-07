# Banking-Service

## Development Requirements
Install:
- Java 8
- mysql 

## Getting started
To start the service you need to:
- import in mysql the startup database defined in the https://github.com/UTCN-SD-PS/2019-30433-a1-romina1601/blob/master/sqlscript/bankingservice.sql
- configure the username and password of your mysql user in https://github.com/UTCN-SD-PS/2019-30433-a1-romina1601/blob/master/src/main/resources/application.properties

## Starting the service
Go to bankingservice folder and execute the following command: 
gradlew.bat bootRun

## Accessing the service swagger:
- http://localhost:8080/swagger-ui.html

employee apis: 
- http://localhost:8080/swagger-ui.html#/Account
- http://localhost:8080/swagger-ui.html#/Client

admin apis:
- http://localhost:8080/swagger-ui.html#/Employee

## Calling the service apis:
- each API call is protected with credentials that are configured in https://github.com/UTCN-SD-PS/2019-30433-a1-romina1601/blob/master/src/main/resources/application.properties, as long as the username and the password are stored in the database

## Testing the application:
Let's walk through a simple example:
- click on the Client tag
- click on a method you want to test, in our case getAllClients
- click on the Try it out button
- click on the Execute button
  -- alternatively, for other methods, you shoud type the required inputs, in their respective fields
- a sign-in form will pop up and you should enter the username and the password (*be careful at the type of user required for each method*)
- if the username and passowrd are inside the database and the user type is correct, you will get as response a list containing all the clients in the database; otherwise you will get as response a message stating that your access has been forbidden
