package org.spring.pizzarazzi_alarm.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi_alarm.enums.OrderStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long memberId;
    private Long orderId;
    private OrderStatus orderStatus;
    private Long totalPrice;
}
