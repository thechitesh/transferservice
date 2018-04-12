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
5. Balance can not be negative
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


### Version Management
Git is used for version control system.


### Build And RUN

**On Windows Operating System**

To build and run the application, you need to have java and maven installed on the machine.
Then, follow the below steps.
1. git clone https://github.com/thechitesh/transferservice.git
2. cd transferservice
3. mvn clean install
4. cd target
5. java -jar transferservice-1.0-SNAPSHOT.jar 

Once the application start,the webservice endpoints can be accessed at following URL 

`http://localhost:9080/transferapplication/v1/accounts`

`http://localhost:9080/transferapplication/v1/initiatetransfer` 



**`The Spring Boot Transfer Service Application can also be dployed on AWS cloud, but it is not in the scope of this project.`**


### Swagger UI


The Rest specification for the service is defined in the **_transferapplication.yaml_** file. This file can be opened into Swagger Editor, it gives a good
insight of the specification on which the services a build.

Also, Spring Boot Swagger support is added in the project. The following URL
will give the details of available REST service exposed by the application.
Swagger UI integration is also good for testing the services. 

`http://localhost:9080/transferapplication/swagger-ui.html` 

## Error Codes and Text 

In case of failure, the error json is send to the user, which has a code and
human readable text. Below are the tabular representation of error code and the text.
These error codes are mapped with the approporiate HTTP Status Codes.

|Error Code | HTTP Status Code | Error Message  |
| --------  |:-------------:| -----|
|TS_001 |400|   Balance can not be null
|TS_002	|400|   Balance should be a positive number
|TS_003	|400|   Account Name can not empty
|TS_004 |409|	Account name already present
|TS_005	|400|	Transfer Amount should be greater than zero
|TS_006	|403|	Balance not sufficient for transfer
|TS_008	|404|	Initiating Party Account Not Found
|TS_009	|404|	Initiating Party Account Name can not empty
|TS_010	|404|	Counter Party Account Not Found
|TS_011	|400|	Counter Party Account Name can not be empty
|TS_012	|400|	Invalid Id is supplied
|TS_013	|404|	Account Not Found
|TS_014	|400|	Transfer can not be initiated between same accounts
|TS_015	|403|	Balance after trasaction exceeds limit of Balance limit of 99999


 
