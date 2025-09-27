package com.adoption.ms2requests.dto;

import java.time.Instant;
import java.util.UUID;

public record ApplicationView(
        UUID id,
        UUID userId,
        UUID petId,
        Instant requestDate,
        String status,
        Instant statusDate,
        String message
) {}
