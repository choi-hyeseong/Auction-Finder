package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickName(String nickName);

    boolean existsByUserId(String userId);

    boolean existsByNickName(String nickName);
}
