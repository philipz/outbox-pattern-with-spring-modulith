package example;

public record OrderCreated(Long id, String product, OrderStatus status) {
    public enum OrderStatus {
        CREATED, UPDATE, COMPLETED
    }
}
