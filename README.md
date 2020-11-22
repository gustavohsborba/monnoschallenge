# Monnos Star Wars API

This sample API <API DESCRIPTION>
It consists of <NUMBER OF ENDPOINTS> endpoints:

* POST / - description
* GET / - description
* DELETE /- description

As a ready-to-production API, there is also a webpage with the documentation, using OpenAPI Swagger, 
available on the url:
* /swagger-ui.html - a webpage with the swagger2 interactive OpenAPI documentation 

### Table of Contents

- [Monnos Star Wars API](#monnos-star-wars-api)
  * [Getting Started](#getting-started)
    + [Built With](#built-with)
    + [Prerequisites](#prerequisites)
  * [Deployment](#deployment)
    + [Using Maven](#using-maven)
    + [Using Docker](#using-docker)
  * [Testing](#testing)
  * [Accessing API](#accessing-api)
  * [Authors](#authors)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project.


### Built With

* [Spring](https://spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Container used
* [MongoDB](https://www.mongodb.com/) - Database used

### Prerequisites

In order to execute this application, you'll need a computer equipped with Docker or JVM and Maven installed.

## Deployment

### Using maven

If you have maven installed, you can run tests, build and execute this application via maven.

In order to build, run the command below:
```bash
mvn clean install
```

Finally, run application:
```bash
java -jar target/challenge-1.0.jar
``` 

### Using Docker

It is possible to build this application with a ready-to-production dockerfile.

To generate the image, first use the command:
```bash
docker image build -t monnos-sw-api .
```
The dockerfile in the main folder builds this application in a first maven container, 
and after that, copies the bootstrapped jar to a new container, with only jvm.
Separating build and production environment, it ensures also a lightweight environment for the application, 
that runs as a jar isolated in it's own container.

After building, run the container to start the application: 
```bash
 docker container run -p 8080:8080 monnos-sw-api
```

## Testing

If you're using docker, the dockerfile alredy executes unit and integration tests in the image building process. 
In order to run integration tests with maven, just run the command below after mvn install:
```bash
mvn clean integration-test
```

## Accessing API:

After executing maven or docker commands, you can access the API using the following URL:
```
http://localhost:8080/
```

For OpenAPI docs, follow on your browser:
```
http://localhost:8080/swagger-ui.html
```


## Authors

* **Gustavo Borba** - [LinkedIn Profile](https://www.linkedin.com/in/gustavohsborba/) - [Github Profile](https://github.com/gustavohsborba) - [gustavohsborba@gmail.com](mailto:gustavohsborba@gmail.com)
