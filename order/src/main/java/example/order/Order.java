package example.order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String product) {
        this.product = product;
        this.status = OrderStatus.CREATED;
    }

    public enum OrderStatus {
        CREATED, COMPLETED
    }
}
