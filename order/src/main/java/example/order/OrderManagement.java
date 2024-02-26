package example.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.order.Order.OrderStatus;

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

        events.publishEvent(new OrderCreated(order.getId(), order.getProduct(), order.getStatus()));

        log.info("Order created");

        return order;
    }

    @WithSpan("update-order")
    public Order update(Long id, @SpanAttribute("product") String product) {
        @SuppressWarnings("null")
        var order = orders.findById(id).get();
        order.setProduct(product);
        order.setStatus(OrderStatus.UPDATE);
        orders.save(order);

        events.publishEvent(new OrderCreated(order.getId(), order.getProduct(), order.getStatus()));

        log.info("Order updated");

        return order;
    }

    @WithSpan("get-order")
    public Order findById(Long id) {
        @SuppressWarnings("null")
        var order = orders.findById(id).get();
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> fetch() {
        return orders.findAll();
    }
}
