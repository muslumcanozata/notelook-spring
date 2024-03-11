package com.example.notelook.controller;

import com.example.notelook.model.entity.Note;
import com.example.notelook.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteNoteById(@PathVariable Long id) {
        noteService.softDeleteNoteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(Note note) {
        noteService.create(note);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(Note note) {
        noteService.update(note);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAll() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<Note>> findAllByUserId(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(noteService.findAllByUserId(userId));
    }
}
