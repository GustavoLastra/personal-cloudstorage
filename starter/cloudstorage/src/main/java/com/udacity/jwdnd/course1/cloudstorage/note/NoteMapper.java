package com.udacity.jwdnd.course1.cloudstorage.note;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNoteList(Integer userId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId} AND noteId = #{noteId}")
    Note getNote(Note note);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    boolean saveNote(Note note);

    @Delete("DELETE FROM NOTES WHERE userId = #{userId} AND noteId = #{noteId}")
    boolean deleteNote(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE userId = #{userId} AND noteId = #{noteId}")
    boolean updateNote(Note note);
}
