package example.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderManagement {

    private final OrderRepository orders;
    private final ApplicationEventPublisher events;

    @WithSpan("create-order")
    public Order create(@SpanAttribute("product") String product) {
        var order = orders.save(new Order(product));

        events.publishEvent(new OrderCreated(order.getId(), order.getProduct()));

        log.info("Order created");

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> fetch() {
        return orders.findAll();
    }
}
