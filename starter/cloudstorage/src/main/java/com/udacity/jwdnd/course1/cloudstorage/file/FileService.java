package com.udacity.jwdnd.course1.cloudstorage.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile file, Integer userId) throws IOException {


        return fileMapper.saveFile(new File(0,file.getOriginalFilename(), file.getContentType(),file.getSize(),userId,file.getBytes()));
    }

    public List<File> getFileList(Integer userId) {
        return fileMapper.getFileList(userId);
    }

}
