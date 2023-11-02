# [Educative Game Project] - Character Service

This project serves as an educational example of building a RESTful API for managing character data
stored in a MongoDB database.

This is a part of an educative game project.

## Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Environment Variables](#environment-variables)
- [Deployment](#deployment)

## Project Overview

This project is designed to demonstrate how to create a simple API using Spring Boot and MongoDB.

It provides basic CRUD operations for managing character data.

It is intended for educational purposes and can serve as a starting point for understanding the
development of RESTful APIs with Spring Boot and MongoDB.

It consumes the [equipment service](https://github.com/UPHF-Components-Courses/equipment-service).

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher
- Maven build tool
- Docker & Docker Compose
- Your favorite IDE or code editor

## Getting Started

1. Clone this repository:

```bash
$ git clone git@github.com:UPHF-Components-Courses/character-service.git
```

2. Start the database:

This service needs a Mongo database in order tu run. You can provide your or start one using the
project docker-compose by running:

```bash
$ docker-compose up
```

3. (Optional) Create your local configuration:

You can override the default application by creating your own config file. You can also customize
using env variables ([see below](#environment-variables))

Example:

*application-local.yml*

```yml
spring:
  data:
    mongodb:
      uri: mongodb://mydatabase:27017
      database: my_character_service_db
      auto-index-creation: false
```

4. Run the application:

You can run the app by using the following command:

```bash
$ mvn spring-boot:run
```

If you want to use the custom configuration created above, use this command:

```bash
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Environment variables

Following environment variables are available for customization:

| Name                  | Type    | Default value             | Description                                                 |
|-----------------------|---------|---------------------------|-------------------------------------------------------------|
| PORT                  | Integer | 8080                      | The port the server should start on                         |
| BASE_URL              | String  | /                         | The base path for the service                               |
| DB_URL                | String  | mongodb://localhost:27017 | The url of the mongo database to use                        |
| DB_NAME               | String  | character_service_db      | The name of the database that should be used by the service |
| EQUIPMENT_SERVICE_URL | String  | localhost:8081            | The base url of the equipment service                       |

## Deployment

This project can be pushed to the free temporary image repository [ttl.sh](https://ttl.sh).

Two ways are available:

1. CI/CD

A CI/CD config will automatically build and push the image for following branches:

- main
- release/*

The image can then be pulled with the following command:

```bash
$ docker pull ttl.sh/ecp_character_service:1h
```

Have a look to the [github action config file](.github/workflows/build_push_ttl.yml).

2. Script

A [shell script](ttl_push.sh) is available to push to ttl.sh without CI/CD.

It can be used like this:

```bash
$ ./ttl_push.sh my_service_name
```

The resulting tag will be displayed after script execution.
