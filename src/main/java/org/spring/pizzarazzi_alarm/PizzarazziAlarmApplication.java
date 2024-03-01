package org.spring.pizzarazzi_alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PizzarazziAlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzarazziAlarmApplication.class, args);
    }

}
