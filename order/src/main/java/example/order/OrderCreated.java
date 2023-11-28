package example.order;

import org.jmolecules.event.types.DomainEvent;
import org.springframework.modulith.events.Externalized;

@Externalized
public record OrderCreated(Long id, String product) implements DomainEvent {
}
