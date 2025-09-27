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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID petId;

    @Column(nullable = false)
    private Instant requestDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Instant statusDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
}
