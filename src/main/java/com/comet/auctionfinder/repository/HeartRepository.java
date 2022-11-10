package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByCourtAndAuctionValue(String court, String auctionValue);
}
