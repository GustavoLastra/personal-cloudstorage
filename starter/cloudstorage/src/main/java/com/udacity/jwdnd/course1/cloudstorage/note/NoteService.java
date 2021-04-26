package com.udacity.jwdnd.course1.cloudstorage.note;

import com.udacity.jwdnd.course1.cloudstorage.credential.CredentialMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(CredentialMapper credentialMapper, NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer saveNote(NoteForm noteForm , Integer userId) {
        return noteMapper.saveNote(new Note(noteForm.getNoteTitle(), noteForm.getNoteDescription(),userId));
    }

    public List<Note> getNoteList(Integer userId) {
        return noteMapper.getNoteList(userId);
    }
}
