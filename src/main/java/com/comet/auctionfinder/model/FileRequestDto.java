package com.comet.auctionfinder.model;


import lombok.Data;

@Data
public class FileRequestDto {

    private String fileName;
    private String modifiedName;
    private Board board;

    public FileRequestDto(String fileName, String modifiedName) {
        this.fileName = fileName;
        this.modifiedName = modifiedName;
    }

    public FileEntity toEntity() {
        return FileEntity.builder().fileName(fileName).modifiedName(modifiedName).board(board).build();
    }
}
