package org.spring.pizzarazzi_alarm.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi_alarm.dto.member.MemberInfoDTO;
import org.spring.pizzarazzi_alarm.dto.member.MemberLoginInfoDTO;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestCheckPasswordDTO;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestMemberLoginDTO;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi_alarm.dto.request.member.RequestMemberUpdateDTO;
import org.spring.pizzarazzi_alarm.exception.DuplicateMemberException;
import org.spring.pizzarazzi_alarm.model.user.Member;
import org.spring.pizzarazzi_alarm.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService  {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String eamil) throws UsernameNotFoundException {
        log.info("MemberService.loadUserByEmail");
        log.info("LOGIN");
        return memberRepository.findByEmail(eamil)
                .orElseThrow(() -> new UsernameNotFoundException(eamil + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    public void signup(RequestMemberSignUpDTO requestMemberSignUpDTO) {
        log.info("MemberService.signup");
        if(memberRepository.existsByEmail(requestMemberSignUpDTO.getEmail())) {
            throw new DuplicateMemberException("이미 가입되어 있는 중복된 이메일입니다.");
        }
        if(memberRepository.existsByNickName(requestMemberSignUpDTO.getNickName())) {
            throw new DuplicateMemberException("이미 가입되어 있는 중복된 닉네임입니다.");
        }

        Member member = requestMemberSignUpDTO.toEntity(requestMemberSignUpDTO.getRoleType());
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }

    public MemberLoginInfoDTO getLoginInfo(RequestMemberLoginDTO requestLoginDto) {
        log.info("MemberService.getLoginInfo");
        Optional<MemberLoginInfoDTO> memberInfoDto = memberRepository.findMemberInfoByEmail(requestLoginDto.getEmail());
        if(memberInfoDto.isPresent()) {
            return MemberLoginInfoDTO.builder()
                    .email(memberInfoDto.get().getEmail())
                    .nickName(memberInfoDto.get().getNickName())
                    .roleType(memberInfoDto.get().getRoleType())
                    .build();
        }
        return new MemberLoginInfoDTO();
    }

    public Member getMember(Long memberId) {
        log.info("MemberService.getMember");
        return memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    public MemberInfoDTO getMemberInfo(Long memberId) {
        log.info("MemberService.getMemberInfo");
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
        return MemberInfoDTO.builder()
                .email(member.getEmail())
                .nickName(member.getNickName())
                .roleType(member.getRoleType())
                .build();
    }

    public MemberInfoDTO checkPassword(Long memberId, RequestCheckPasswordDTO requestCheckPasswordDTO) {
        log.info("MemberService.checkPassword");
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
        if(passwordEncoder.matches(requestCheckPasswordDTO.getPassword(), member.getPassword())) {
            return MemberInfoDTO.builder()
                    .email(member.getEmail())
                    .nickName(member.getNickName())
                    .roleType(member.getRoleType())
                    .build();
        }
        return new MemberInfoDTO();
    }

    public void updateMemberInfo(Long memberId, RequestMemberUpdateDTO memberUpdateDTO) {
        log.info("MemberService.updateMemberInfo");
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
        member.setEmail(memberUpdateDTO.getEmail());
        member.setNickName(memberUpdateDTO.getNickName());
        member.setPassword(passwordEncoder.encode(memberUpdateDTO.getPassword()));
    }

    public void deleteMember(Long memberId) {
        log.info("MemberService.deleteMember");
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));

        if (member != null) {
            memberRepository.delete(member);
        }

    }
}
