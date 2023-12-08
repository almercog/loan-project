## About API simulator loan
Spring Boot, MongoDB and Cucumber(BDD), this project depends 2 components:
- **Jaspert report Component**

 ```
	<dependency>
		<groupId>com.integrator.jasper.report</groupId>
		<artifactId>jasper-report-integrator</artifactId>
		<version>1.0.0</version>
	</dependency>
 ```
- **Mail integrator Component**

 ```
	<dependency>
		<groupId>com.integrator.mail</groupId>
		<artifactId>mail-integrator</artifactId>
		<version>1.0.0</version>
	</dependency>
 ```

## Install Docker MongoDB
- pull and run mongo image from docker hub 

 ```
	docker pull mongo:latest
	docker run -d -p 27017:27017 --name mongodb mongo:latest
 ```

## Config vault properties
- Create file **loan-simulator-dev.json**

 ```JSON
 {
   "spring": {
     "data": {
       "mongodb": {
         "database": "loandb",
         "host": "localhost",
         "port": 27017
       }
     },
     "mail": {
       "default-encoding": "UTF-8",
       "host": "smtp.gmail.com",
       "port": 587,
       "username": "xxxx@gmail.com",
       "password": "<password>",
       "properties": {
         "mail": {
           "smtp": {
             "auth": true,
             "starttls": {
               "enable": true
             }
           }
         }
       },
       "protocol": "smtp",
       "test-connection": false,
       "templates": {
         "path": "templates"
       }
     }
   }
 }
 ```
- Create secret for **dev** profile

 ```
	vault kv put secret/loan-simulator/dev @loan-simulator-dev.json
	vault kv get secret/loan-simulator/dev
 ```

- Create file **loan-simulator.json**

 ```JSON
 {
   "spring": {
     "data": {
       "mongodb": {
         "database": "loandb",
         "host": "mongodb",
         "port": 27017
       }
     },
     "mail": {
       "default-encoding": "UTF-8",
       "host": "smtp.gmail.com",
       "port": 587,
       "username": "xxxx@gmail.com",
       "password": "<password>",
       "properties": {
         "mail": {
           "smtp": {
             "auth": true,
             "starttls": {
               "enable": true
             }
           }
         }
       },
       "protocol": "smtp",
       "test-connection": false,
       "templates": {
         "path": "templates"
       }
     }
   }
 }
 ```
- Create secret for **cloud** profile

 ```
	vault kv put secret/loan-simulator/cloud @loan-simulator.json
	vault kv get secret/loan-simulator/cloud
 ```
## Config bootstrap.yml

Profile **dev** (localhost)

 ```
	spring:
	   application:
	      name: loan-simulator
	   cloud:
	      vault:
	         host: 127.0.0.1
	         port: 8200
	         scheme: http
 ```
 Profile **cloud** (vault)

 ```
	spring:
	   application:
	      name: loan-simulator
	   cloud:
	      vault:
	         host: xxxx.xxx.xxx.xxx
	         port: 8200
	         scheme: http
 ```

## Run Test
- Run test features, using **dev profile** and your **token vault**

 ```
	mvn clean test -Dspring.profiles.active=dev -Dspring.cloud.vault.token=00000000-0000-0000-0000-000000000000
 ```

## Run API in local
- Once we have successfully conducted the tests, we will run our API on the local server.

 ```
	mvn clean package -DskipTests
	java -jar -Dserver.port=9192 -Dspring.profiles.active=dev -Dspring.cloud.vault.token=00000000-0000-0000-0000-000000000000 target/api-simulator-loan-1.0.0.jar 
 ```

## Docker Steps & Commands

Deploying Spring Boot and MongoDB as Containers Using Docker and Docker Compose


- dockerize spring boot application then run spring boot docker image and link that container to mongo container 

 ```
	mvn clean package -DskipTests
	docker build -t api-simulator-loan:1.0 .
	docker run -e "SPRING_CLOUD_VAULT_TOKEN=00000000-0000-0000-0000-000000000000" -p 9191:8080 --name api-simulator-loan --link mongodb:mongo -d api-simulator-loan:1.0
 ```

- check docker running containers, it should display two container ids 

 ```
	docker ps
 ```
- check logs of spring boot image 

 ```
	docker logs api-simulator-loan
 ```
 
- if all good access your api 

```bash
	curl --location --request POST 'http://localhost:9191/api/loan' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	  "payload": {
	    "amount": 10000,
	    "rate": "5",
	    "termType": "M",
	    "repaymentTerm": 140,
	    "withGracePeriod": "S",
	    "gracePeriod": "3",
	    "disbursementDate": "2023-10-03",
	    "email": "almercog@gmail.com"
	  }
	}'
```
- login to mongo terminal to verify records

 ```
	docker exec -it mongodb bash
 ```
- type mongo and enter
- show dbs
- use book
- show collections
- db.book.find().pretty()

## Use Docker Compose

- Kill running container:

```
docker rm <containerId>
```
