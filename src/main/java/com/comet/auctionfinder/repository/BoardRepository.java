package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    List<Board> findAllByAuthor_Id(long id);

    Page<Board> findByTitleContains(Pageable pageable, String title);

    Page<Board> findByAuthor_NickNameContains(Pageable pageable, String author);

    Page<Board> findByContentContains(Pageable pageable, String content);
}
