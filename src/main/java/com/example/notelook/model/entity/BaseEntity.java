package com.example.notelook.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    private LocalDate updatedAt;
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }
    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(LocalDate.now());
    }
}