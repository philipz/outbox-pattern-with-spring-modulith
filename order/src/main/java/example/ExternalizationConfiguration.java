package example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

import example.order.OrderCreated;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ExternalizationConfiguration {

    @Value("${order.order-created-kafka-topic}")
    private String orderCreatedKafkaTopic;

    private final GenericApplicationContext applicationContext;

    EventExternalizationConfiguration eventExternalizationConfiguration() {

        return EventExternalizationConfiguration.externalizing()
                .select(EventExternalizationConfiguration.annotatedAsExternalized())
                .route(OrderCreated.class, orderCreated -> RoutingTarget
                        .forTarget(orderCreatedKafkaTopic)
                        .andKey(String.valueOf(orderCreated.id())))
                .build();
    }

    /**
     * The {@code EventExternalizationConfiguration} bean is registered manually because the topic
     * name {@code orderCreatedKafkaTopic} is not injected at the time of automatic bean
     * registration.
     */
    @PostConstruct
    public void init() {

        this.applicationContext.registerBean(EventExternalizationConfiguration.class,
                this::eventExternalizationConfiguration,
                bd -> bd.setPrimary(true));
    }
}
