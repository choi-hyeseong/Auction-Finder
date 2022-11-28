package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.FileResponseDto;
import com.comet.auctionfinder.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private FileRepository repository;

    @Transactional(readOnly = true)
    public Optional<FileResponseDto> getImage(long id) {
        return repository.findById(id).map(FileResponseDto::new);
    }
}
