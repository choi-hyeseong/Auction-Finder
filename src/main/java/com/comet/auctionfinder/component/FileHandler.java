package com.comet.auctionfinder.component;

import com.comet.auctionfinder.dto.FileRequestDto;
import com.comet.auctionfinder.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class FileHandler {

    private static final String DIR = System.getProperty("user.dir") + "\\files";

    public List<FileRequestDto> saveFile(List<MultipartFile> files) {
        List<FileRequestDto> fileList = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String origin = file.getOriginalFilename();
                if (origin != null) {
                    String ext = extractExtWithDot(origin);
                    String modifiedName = UUID.randomUUID() + ext;
                    String savePath = DIR + "\\" + modifiedName;
                    if (!new File(DIR).exists())
                        new File(DIR).mkdir();
                    try {
                        FileRequestDto dto = new FileRequestDto(origin, modifiedName);
                        file.transferTo(new File(savePath));
                        fileList.add(dto);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileList;
    }

    public Path getFilePath(FileResponseDto dto) {
        return Paths.get(DIR + "\\" + dto.getModifiedName());
    }
    public Resource fileDtoToResource(FileResponseDto dto) throws IOException {
        return  new InputStreamResource(Files.newInputStream(getFilePath(dto)));
    }
    public String extractExtWithDot(String origin) {
        return "." + origin.substring(origin.lastIndexOf(".") + 1);
    }
}
