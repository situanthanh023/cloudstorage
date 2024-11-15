package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
    private final UserMapper userMapper;
    private final NotesMapper notesMapper;

    public NotesService(UserMapper userMapper, NotesMapper notesMapper) {
        this.userMapper = userMapper;
        this.notesMapper = notesMapper;
    }

    public void addNote(String title, String description, String userName) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Note note = new Note(0, title, description, userId);
        notesMapper.addNoteByNoteId(note);
    }

    public Note[] getAllNotes(Integer userId) {
        return notesMapper.findNotesByUser(userId);
    }

    public Note getNote(Integer noteId) {
        return notesMapper.getNote(noteId);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNoteById(noteId);
    }

    public void updateNote(Integer noteId, String title, String description) {
        notesMapper.updateNoteById(noteId, title, description);
    }
}
