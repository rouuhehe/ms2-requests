package com.adoption.ms2requests.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name="requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId; // FK a users.id

    @Column(nullable = false)
    private UUID petId; // FK a pets.id (en MS1)

    @Column(nullable = false)
    private Instant requestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(nullable = false)
    private Instant statusDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    public enum Status {
        pending, approved, rejected
    }
}
