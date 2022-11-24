package com.comet.auctionfinder.repository;

import com.comet.auctionfinder.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
