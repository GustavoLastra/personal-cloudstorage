package com.udacity.jwdnd.course1.cloudstorage.note;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean saveNote(Note note) {
        if (note.getNoteId() != null && noteMapper.getNote(note) != null) {
            return noteMapper.updateNote(note);
        }
        return noteMapper.saveNote(note);
    }


    public List<Note> getNoteList(Integer userId) {
        return noteMapper.getNoteList(userId);
    }

    public boolean deleteNote(Integer noteId, Integer userId) {
        return noteMapper.deleteNote(noteId, userId);
    }

}
