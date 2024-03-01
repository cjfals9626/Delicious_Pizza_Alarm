package org.spring.pizzarazzi_alarm.service.auth;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestMemberLoginDTO;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi_alarm.dto.response.ResponseLoginDTO;
import org.spring.pizzarazzi_alarm.service.member.MemberService;
import org.spring.pizzarazzi_alarm.util.jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

    @Transactional
    public void signup(RequestMemberSignUpDTO requestMemberSignUpDTO) {
        memberService.signup(requestMemberSignUpDTO);
    }

    @Transactional
    public ResponseLoginDTO login(RequestMemberLoginDTO requestLoginDto) throws RuntimeException {
        // 1. Login Email/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String accessToken = tokenProvider.createToken(authentication);

        // 5. 토큰 발급
        return ResponseLoginDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
