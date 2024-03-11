package com.example.notelook.service;

import com.example.notelook.exception.NoteNotFoundException;
import com.example.notelook.model.entity.Note;
import org.springframework.stereotype.Service;
import com.example.notelook.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void softDeleteNoteById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        note.setDeleted(true);
        noteRepository.save(note);
    }

    public void create(Note note) {
        noteRepository.save(note);
    }

    public void update(Note note) {
        noteRepository.save(note);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<Note> findAllByUserId(Long userId) {
        return noteRepository.findAllByUserId(userId);
    }
}
