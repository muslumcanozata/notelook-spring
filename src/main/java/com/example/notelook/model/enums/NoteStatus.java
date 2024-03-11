package com.example.notelook.model.enums;

import lombok.Getter;
@Getter
public enum NoteStatus  {

    IN_PROGRESS("IN_PROGRESS"),
    PENDING("PENDING"),
    BACKLOG("BACKLOG"),
    DONE("DONE"),
    TO_DO("TO_DO"),
    CANCELLED("CANCELLED");

    private final String value;

    NoteStatus(String value) {
        this.value = value;
    }

}