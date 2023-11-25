# Implementing Outbox Pattern with Spring Modulith

This project is a demonstration of Outbox pattern with Spring Modulith library using Event Externalization.

Read the accompanying blog post here: <INSERT BLOG LINK>

## Application Architecture

<img height="60%" src="outbox-pattern-with-spring-modulith.png" title="Application architecture of Outbox Pattern with Spring Modulith" width="60%"/>

## Project Requirements

* JDK 21
* Spring Boot 3.2
* Docker (to run locally)

## Prepare the application

To compile and build the docker images, run below command:

```bash
mvn spring-boot:build-image
```

This will generate two docker images locally - `order:0.0.1-SNAPSHOT` and `notification:0.0.1-SNAPSHOT`.

## Run the application locally

The project comes with a docker compose file which spins up the application as well as the dependencies including Apache Kafka with kRaft and Zipkin for collecting traces. After completing the steps in "Prepare the application", run below command to start the application:

```bash
docker-compose up
```

## Run the application with Axual

To run the application with a Kafka cluster from Axual, follow below steps:

1. Open the file `order/src/main/resources/application-axual.yaml` and update the SASL username and password from Axual (read the accompanying blog to understand how to obtain the credentials). Repeat the steps for the file `notification/src/main/resources/application-axual.yaml`.

    ```yaml
    spring:
      kafka:
        producer:
          properties:
            sasl:
              jaas:
                config: org.apache.kafka.common.security.scram.ScramLoginModule required username="<USERNAME>" password="<PASSWORD>";
    ```

2. Run below command to start the Order monolith with `axual` Spring profile:
    ```bash
    cd order
    mvn spring-boot:run -Dspring-boot.run.profiles=axual
    ```

3. Run below command to start the Notification service with `axual` Spring profile:
    ```bash
    cd notification
    mvn spring-boot:run -Dspring-boot.run.profiles=axual
    ```

4. Trigger the order creation flow with below command:
    ```bash
    curl -X POST http://localhost:8080/orders -d "product=Coffee"
    ```
