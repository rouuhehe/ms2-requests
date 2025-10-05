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

    @Type("uuid-char")
    @Column(nullable = false, length = 36, columnDefinition = "char(36)")
    private UUID userId;

    @Type("uuid-char")
    @Column(nullable = false, length = 36, columnDefinition = "char(36)")
    private UUID petId;

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
