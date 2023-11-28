package example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class);
    }

    @KafkaListener(topics = "#{'${notification.order-created-kafka-topic}'}", groupId = "#{'${notification.group-id}'}")
    public void notify(OrderCreated event) {
        log.info("Notifying user for created order {} and product {}", event.id(), event.product());
    }
}