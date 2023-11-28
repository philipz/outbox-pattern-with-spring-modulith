package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "example")
@SpringBootApplication
public class OrderMonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMonolithApplication.class, args);
    }

}
