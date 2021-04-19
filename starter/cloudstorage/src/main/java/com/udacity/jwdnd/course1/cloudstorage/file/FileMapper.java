package com.udacity.jwdnd.course1.cloudstorage.file;
import com.udacity.jwdnd.course1.cloudstorage.file.models.File;
import com.udacity.jwdnd.course1.cloudstorage.user.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    File[] getFileList(User user);

    @Insert("INSERT INTO FILES (fileName, fileSize, fileData ,contentType, userId) VALUES (#{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);
}
