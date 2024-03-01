package org.spring.pizzarazzi_alarm.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestMemberUpdateDTO {
    private String email;
    private String nickName;
    private String password;
}
