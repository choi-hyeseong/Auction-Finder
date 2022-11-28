package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> deleteAllByMember_Id(long id);

    List<Reply> findAllByMember_Id(long id);
}
