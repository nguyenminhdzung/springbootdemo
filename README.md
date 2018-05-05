# Spring Boot Demo

## Developer Guide

### Maven
This demo source code uses Maven 3+ for build & testing. You can download and install Maven from https://maven.apache.org/download.cgi.

### Database
- Install mysql 5.7+
- Create DB and run db_script.sql
- Config application.yml (in oauth-server, backend-api), e.g:
    ```
    spring:
        ...
        datasource:
            username: <db username>
            password: <db password>
            driver-class-name: com.mysql.jdbc.Driver
            url: jdbc:mysql://localhost/demo?autoReconnect=true&useUnicode=true
            testWhileIdle: true
            timeBetweenEvictionRunsMillis: 3600000
            validationQuery: SELECT 1
        jpa:
            database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
            properties.hibernate.show_sql: true
        ...

### Bootup the System

- Start MySQL
- Build the project using **build.bat**, it will create built files in **release** folder. Then start following services
    + oauth-server (8585)
    + backend-api (8888)
using **java** command
    ```
    start "oauth-server" java -Xmx512m -jar oauth-server.jar
    start "backend-api" java -Xmx1024m -jar backend-api.jar
    ```

### API spec

#### Oauth-Server 
Base URL: http://localhost:8585
- /oauth/token 
    + Method: POST
    + Request headers:
        + Authorization: 'Basic <base64 of client-id:client-secret>'
        + Content-type: application/x-www-form-urlencoded
    + Params:
        + grant_type: 'password' or 'refresh_token'
        + username: <username> (in case of 'password' grant_type)
        + password: <password> (in case of 'password' grant_type)
        + refresh_token: <refresh_token>  (in case of 'refresh_token' grant_type)
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
        "access_token":"43cf35dc-31cd-49a2-913f-daa0b90d1e0d",
        "token_type":"bearer",
        "refresh_token":"06f91b65-a9c1-459d-9968-18b745ca5aa4",
        "expires_in":1745,
        "scope":"backend"
    }
    ```

#### Backend-API
Base URL: http://localhost:8888/v0
- /version
    + Method: GET
    + Content-type: Text/Plain
    + Response: 'demo version'

- /role/all
    + Method: GET
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
        "success": true,
        "resultCode": "Success",
        "devMessage": null,
        "message": "Success",
        "data": [
            {
                "id": "159edcbe-cf7f-458c-af22-022519990420",
                "name": "ROLE_ADMIN"
            },
            {
                "id": "497bf00b-3f83-405d-b9f7-bf050884ba85",
                "name": "ROLE_USER"
            }
        ]
    }
    ```

- /role/get/{id}
    + Method: GET
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Path Params:
        + id: ID of role to get
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
        "success": true,
        "resultCode": "Success",
        "devMessage": null,
        "message": "Success",
        "data": {
            "id": "497bf00b-3f83-405d-b9f7-bf050884ba85",
            "name": "ROLE_USER"
        }
    }
    ```
	
- /user/list
    + Method: GET
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
	+ Query Params:
		+ page: pageIndex (Default 0)
		+ size: pageSize (Default 10)
		+ field: sort field 
		+ direction: sort direction 
		+ keyword: search keyword 
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"data": [
				{
					"id": "4c9ec1dd-be6e-4084-b2d9-7fefe320e611",
					"username": "admin",
					"firstname": "Admin",
					"lastname": "Admin",
					"email": "admin@demo.com",
					"address1": null,
					"address2": null,
					"address3": null,
					"roles": [
						"ROLE_ADMIN"
					]
				}
			],
			"size": 10,
			"totalElements": 1,
			"totalPages": 1,
			"number": 0
		}
	}
    ```

- /user/get/{id}
    + Method: GET
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Path Params:
        + id: ID of user to get
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "4c9ec1dd-be6e-4084-b2d9-7fefe320e611",
			"username": "admin",
			"firstname": "Admin",
			"lastname": "Admin",
			"email": "admin@demo.com",
			"address1": null,
			"address2": null,
			"address3": null,
			"roles": [
				"ROLE_ADMIN"
			]
		}
	}
    ```
	
- /user/create
    + Method: POST
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
	+ Sample request:
	```
	{
		"username": "test",
		"password": "test"
		"firstname": "test firstname",
		"lastname": "test lastname",
		"email": "test@demo.com",
		"address1": "test address 1",
		"address2": "test address 2",
		"address3": "test address 3",
		
		"roles": ["ROLE_ADMIN", "ROLE_USER"]
	}
	```
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "a5e56cda-a627-495d-b9cc-95da65872134",
			"username": "test",
			"firstname": "test firstname",
			"lastname": "test lastname",
			"email": "test@demo.com",
			"address1": "test address 1",
			"address2": "test address 2",
			"address3": "test address 3",
			"roles": [
				"ROLE_ADMIN",
				"ROLE_USER"
			]			
		}
	}
	```
	
- /user/update
    + Method: POST
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
	+ Sample request:
	```
	{
		"id": "a5e56cda-a627-495d-b9cc-95da65872134",
		"username": "test",
		"firstname": "updated firstname",
		"lastname": "updated lastname",
		"email": "updated@demo.com",
		"address1": "updated address 1",
		"address2": "updated address 2",
		"address3": "updated address 3",
		
		"roles": ["ROLE_USER"]
	}
	```
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "a5e56cda-a627-495d-b9cc-95da65872134",
			"username": "test",
			"firstname": "updated firstname",
			"lastname": "updated lastname",
			"email": "updated@demo.com",
			"address1": "updated address 1",
			"address2": "updated address 2",
			"address3": "updated address 3",
			"roles": [
				"ROLE_USER"
			]
		}
	}
	```
	
- /user/delete/{id}
    + Method: DELETE
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Path Params:
        + id: ID of user to delete
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": null,
		"devMessage": null,
		"message": "Success",
		"data": null
	}
    ```
	
- /profile
    + Method: GET
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "4c9ec1dd-be6e-4084-b2d9-7fefe320e611",
			"username": "admin",
			"firstname": "Admin",
			"lastname": "Admin",
			"email": "admin@demo.com",
			"address1": null,
			"address2": null,
			"address3": null,
			"roles": [
				"ROLE_ADMIN"
			]
		}
	}
    ```

- /profile
    + Method: POST
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
	+ Sample request:
	```
	{
		"firstname": "updated admin",
		"lastname": "updated admin",
		"email": "updated_admin@demo.com",
		"address1": "admin address 1",
		"address2": "admin address 2",
		"address3": "admin address 3"		
	}
	```
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "4c9ec1dd-be6e-4084-b2d9-7fefe320e611",
			"username": "admin",
			"firstname": "updated admin",
			"lastname": "updated admin",
			"email": "updated_admin@demo.com",
			"address1": "admin address 1",
			"address2": "admin address 2",
			"address3": "admin address 3",
			"roles": [
				"ROLE_ADMIN"
			]
		}
	}
	```
	
- /profile/changePassword
    + Method: POST
    + Request headers:
        + Authorization: 'Bearer <access token>'
    + Content-type: Application/JSON
	+ Sample request:
	```
	{
		"password": "admin",
		"newPassword": "admin1"
	}
	```
    + Sample response:
    ```
    {
		"success": true,
		"resultCode": "Success",
		"devMessage": null,
		"message": "Success",
		"data": {
			"id": "4c9ec1dd-be6e-4084-b2d9-7fefe320e611",
			"username": "admin",
			"firstname": "updated admin",
			"lastname": "updated admin",
			"email": "updated_admin@demo.com",
			"address1": "admin address 1",
			"address2": "admin address 2",
			"address3": "admin address 3",
			"roles": [
				"ROLE_ADMIN"
			]
		}
	}
	```