package com.udacity.jwdnd.course1.cloudstorage.file;

import com.udacity.jwdnd.course1.cloudstorage.file.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile file, Integer userId) throws IOException {
        return fileMapper.saveFile(new File(file.getOriginalFilename(),file.getSize(),file.getBytes(),file.getContentType(),userId));
    }

}
