package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileResponseDto {

    private long id;
    private String fileName;
    private String modifiedName;

    public FileResponseDto(FileEntity entity) {
        this.id = entity.getId();
        this.fileName = entity.getFileName();
        this.modifiedName = entity.getModifiedName();
    }
}
