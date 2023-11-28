package example.shipping;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.order.OrderCreated;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Shipping {

    @WithSpan("shipping-handle-order-created")
    @ApplicationModuleListener
    void on(@SpanAttribute("event") OrderCreated event) {
        ship(event.id());
    }

    private void ship(Long orderId) {
        log.info("Started shipping for order {}", orderId);
    }
}
