package org.spring.pizzarazzi_alarm.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginDTO {
    private String accessToken;
}
