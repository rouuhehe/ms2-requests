package com.adoption.ms2requests.dto;

import java.util.UUID;

public class ApplicationRequest {
    private UUID userId;
    private UUID petId;
    private String message;

    public ApplicationRequest() {}

    public ApplicationRequest(UUID userId, UUID petId, String message) {
        this.userId = userId;
        this.petId = petId;
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
