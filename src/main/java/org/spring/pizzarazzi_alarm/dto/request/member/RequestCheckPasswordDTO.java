package org.spring.pizzarazzi_alarm.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestCheckPasswordDTO {
    private String password;
}
