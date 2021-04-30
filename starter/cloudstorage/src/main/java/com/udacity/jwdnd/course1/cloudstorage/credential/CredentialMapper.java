package com.udacity.jwdnd.course1.cloudstorage.credential;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialList(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId} AND credentialId = #{credentialId}")
    Credential getCredential(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key ,password, userId) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    boolean saveCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE userId = #{userId} AND credentialId = #{credentialId}")
    boolean deleteCredential(Integer credentialId, Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{userName}, password = #{password} WHERE userId = #{userId} AND credentialId = #{credentialId}")
    boolean updateCredential(Credential credential);
}
