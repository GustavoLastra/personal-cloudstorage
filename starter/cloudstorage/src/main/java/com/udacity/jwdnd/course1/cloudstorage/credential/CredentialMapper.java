package com.udacity.jwdnd.course1.cloudstorage.credential;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    Credential[] getCredentialList(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key ,password, userId) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int saveCredential(Credential credential);
}
