package com.udacity.jwdnd.course1.cloudstorage.credential;

import com.udacity.jwdnd.course1.cloudstorage.file.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<Credential> getCredentialList(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key ,password, userId) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer saveCredential(Credential credential);
}
