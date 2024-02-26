package example.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderManagement orders;

    public OrderController(OrderManagement orders) {
        this.orders = orders;
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody String product) {
        var order = orders.create(product);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> viewOrders() {
        return ResponseEntity.ok(orders.fetch());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> findorder(@PathVariable Long id) {
        return ResponseEntity.ok(orders.findById(id));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody String product) {
        var order = orders.update(id, product);
        return ResponseEntity.ok(order);
    }
}
