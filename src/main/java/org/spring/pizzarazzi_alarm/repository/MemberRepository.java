package org.spring.pizzarazzi_alarm.repository;

import org.spring.pizzarazzi_alarm.dto.member.MemberLoginInfoDTO;
import org.spring.pizzarazzi_alarm.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickname);

    @Query("select new org.spring.pizzarazzi_alarm.dto.member.MemberLoginInfoDTO(m.email, m.nickName, m.roleType) from Member m where m.email like :email")
    Optional<MemberLoginInfoDTO> findMemberInfoByEmail(@Param("email") String email);

}
