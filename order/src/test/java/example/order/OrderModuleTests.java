package example.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.SendResult;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ApplicationModuleTest
class OrderModuleTests {

    @Container
    @ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer("postgres:16.1");

	@DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        //registry.add("spring.datasource.url", postgres::getJdbcUrl);
        postgres.start();
    }

    @MockBean
    KafkaOperations<?, ?> kafkaOperations;

    @Autowired
    OrderManagement orders;

    @Test
    void shouldTriggerOrderCreatedEvent(Scenario scenario) {

        when(kafkaOperations.send(any(), any(), any())).then(invocation -> {
            log.info("Sending message key {}, value {} to {}.", invocation.getArguments()[1], invocation.getArguments()[2], invocation.getArguments()[0]);
            return CompletableFuture.completedFuture(new SendResult<>(null, null));
        });

        scenario.stimulate(() -> orders.create("Coffee"))
                .andWaitForEventOfType(OrderCreated.class)
                .toArriveAndVerify(event -> assertThat(event.product()).isEqualTo("Coffee"));
    }
}
