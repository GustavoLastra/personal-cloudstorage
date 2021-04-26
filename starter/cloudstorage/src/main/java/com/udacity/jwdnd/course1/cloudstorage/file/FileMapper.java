package com.udacity.jwdnd.course1.cloudstorage.file;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFileList(Integer userId);

    @Insert("INSERT INTO FILES (fileName, fileSize, fileData ,contentType, userId) VALUES (#{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Delete("DELETE FROM files WHERE fileid = #{fileId}")
    boolean deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);
}
