package org.spring.pizzarazzi_alarm.dto.request.member;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestMemberLoginDTO {
    private String email;
    private String password;
}
