package com.udacity.jwdnd.course1.cloudstorage.file;

public class File {
    private int fileId;
    private String fileName;
    private long fileSize;
    private byte[] fileData;
    private String contentType;
    private int userId;

    public File(String fileName, long fileSize, byte[] fileData, String contentType, Integer userId) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.contentType = contentType;
        this.userId = userId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
