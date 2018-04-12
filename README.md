# Transfer Service Application

This project is build with Spring Boot framework. The application enables accounts and transfer
rest services. Account services enables creating of account and Transfer services enables amount
transfer between different account.


## Build

Project uses maven as build and dependency management tool.

1) For build : `mvn clean install`
2) For test : `mvn test`


## Version Management
Git is used for version control system.

## Deployment
The transfer service application, when build by maven gives a jar file which has embedded tomcat in it.
The jar file can be run from any machine which has the java installed.

The application can be started with following commands.

`java -jar transferservice-1.0-SNAPSHOT.jar`

Once the application start,the Rest end points can be accessed at following URL 

`http://localhost:9080/transferapplication/v1/accounts`
`http://localhost:9080/transferapplication/v1/initiatetransfer` 



`The Spring Boot Transfer Service Application can also be dployed on AWS cloud, but it is not in the scope of this project.`


## Swagger UI

Spring Boot Swagger support is also added in the project. The following URL
will give the details of available REST service exposed by the applicaiton and also it provides a 
way to test those endpoints.

`http://localhost:9080/transferapplication/swagger-ui.html` 
