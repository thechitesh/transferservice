# Transfer Service Application

**ABOUT**

The business requirement is that a user should be able to create a Account and further he/she is able to transfer amount from one account
to other. 

To cater to this requirement, *Transfer Service Application* is build, which make webservice available for this purpose.
The web service are build around an Account Resource and a Transfer service is build to enable transfer between two accounts.

Accounts : `/v1/acoounts`

Transfer : `/v1/initiatetransfer`

The account service enables user to create several accounts with minimum account resource properties like _name_ and _balance_

The transfer service can make transfer from one account to other and vice-versa.
More information about the resoureces are mentioned in _transferapplication.yaml_ file

**Business Rules**

Accont Resource
1. Account can be created with name and balance
2. Two accounts with same name can not exists
3. Name can not be null/empty
4. Balance can be 0 or decimal
5  Balance can not be negative
6. Starting account balance can be in a range of 0  to 99999

Transfer
1. Transfer can be initiated between two account by providing account names of initiating party and counter party and amount.
2. Transfer can only be initiated between two different account names
3. Initiating party account name should not be null or empty
4. Counter party account name should be null or empty
5. Both the account should existing in database.
6. Initiating party account should have sufficient account balance.
7. Transfer can not be initiated with negative amount 
8. Transfer amount should be in a range of 0 to 99999


**Technical**

This project is build with java 8 using Spring Boot framework. Spring Boot is the obivious choice
because it reduces the unnecessary effort of configuring environment like server and databse.
Here the embedded tomcat server is used, and runtime derby db is used for storing data.
The limitation is that all data persists till the server is running. Upon restart the exsiting data from 
database is wiped out.
The application is build as per REST specification and I have tried to use appropriate HTTP method for the service.
On validation failure the appropriate HTTP status codes are mapped.

HTTP Operation Used : GET/POST

HTTP Status Code User : 200,201,400,404,409

The project is having sufficient Unit Test as well Integration Test to verify compliance 
to business rules during build phase.



1. Java 1.8
2. Spring Boot
3. Maven
4. Mockito/Spring Test
5. Swagger UI

-- Out of Scope --

1. Unit test Coverage
2. Release Version of build

### Build

Project uses maven as build and dependency management tool.

1) For build : `mvn clean install`
2) For test : `mvn test`


### Version Management
Git is used for version control system.

### Deployment
The transfer service application, when build by maven gives a jar file which has embedded tomcat in it.
The jar file can be run from any machine which has the java installed.

The application can be started with following commands.

`java -jar transferservice-1.0-SNAPSHOT.jar`

Once the application start,the Rest end points can be accessed at following URL 

`http://localhost:9080/transferapplication/v1/accounts`
`http://localhost:9080/transferapplication/v1/initiatetransfer` 



**`The Spring Boot Transfer Service Application can also be dployed on AWS cloud, but it is not in the scope of this project.`**


### Swagger UI


The Rest specification for the service is defined in the **_transferapplication.yaml_** file. This file can be opened into Swagger Editor.

Also, Spring Boot Swagger support is added in the project. The following URL
will give the details of available REST service exposed by the applicaiton and also it provides a 
way to test those endpoints.

`http://localhost:9080/transferapplication/swagger-ui.html` 

## Error Codes and Text 


|Error Code | HTTP Status Code | Error Message  |
| --------  |:-------------:| -----:|
|TS_001    |	400	| Balance can not be null
 
