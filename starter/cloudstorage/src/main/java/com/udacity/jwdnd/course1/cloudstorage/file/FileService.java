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

    public boolean saveFile(MultipartFile file, Integer userId) throws IOException {
        if (fileMapper.getFileByName(file.getOriginalFilename(), userId) == null) {
            return fileMapper.saveFile(new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), userId, file.getBytes()));
        }
        return false;
    }

    public List<File> getFileList(Integer userId) {
        return fileMapper.getFileList(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public boolean deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
