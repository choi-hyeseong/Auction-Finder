package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);
}
