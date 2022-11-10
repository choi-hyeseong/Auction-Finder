package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Heart;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.model.MemberHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberHeartRepository extends JpaRepository<MemberHeart, Long> {

    Optional<MemberHeart> findByHeartAndMember(Heart heart, Member member);
}
