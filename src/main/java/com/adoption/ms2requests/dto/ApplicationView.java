package com.adoption.ms2requests.dto;

import java.time.Instant;
import java.util.UUID;

public record ApplicationView(
        UUID id,
        UUID userId,
        UUID petId,
        Instant requestDate,
        com.adoption.ms2requests.model.Request.Status status,
        Instant statusDate,
        String message
) {}
