package com.adoption.ms2requests.service;

import com.adoption.ms2requests.client.PetsClient;
import com.adoption.ms2requests.dto.ApplicationRequest;
import com.adoption.ms2requests.dto.ApplicationView;
import com.adoption.ms2requests.model.Request;
import com.adoption.ms2requests.model.User;
import com.adoption.ms2requests.repo.RequestRepo;
import com.adoption.ms2requests.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    private final RequestRepo requests;
    private final PetsClient petsClient;
    private final UserRepo userRepo;

    public ApplicationService(RequestRepo requests, PetsClient petsClient, UserRepo userRepo) {
        this.requests = requests;
        this.petsClient = petsClient;
        this.userRepo = userRepo;
    }

    public List<ApplicationView> listByUser(UUID userId) {
        return requests.findByUserId(userId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Optional<ApplicationView> detail(UUID userId, UUID applicationId) {
        return requests.findById(applicationId)
                .filter(r -> r.getUserId().equals(userId))
                .map(this::map);
    }

    public ApplicationView create(ApplicationRequest req) {
        boolean exists = petsClient.petExists(req.getPetId());
        if (!exists) {
            throw new RuntimeException("Pet not found");
        }

        Request r = new Request();
        r.setId(UUID.randomUUID());
        r.setUserId(req.getUserId());
        r.setPetId(req.getPetId());
        r.setRequestDate(Instant.from(LocalDateTime.now()));
        r.setStatus(Request.Status.valueOf("pending"));
        r.setStatusDate(Instant.from(LocalDateTime.now()));
        r.setMessage(req.getMessage() != null ? req.getMessage() : "Your request is being processed.");

        Request saved = requests.save(r);

        return map(saved);
    }

    private ApplicationView map(Request r) {
        return new ApplicationView(
                r.getId(),
                r.getUserId(),
                r.getPetId(),
                r.getRequestDate(),
                r.getStatus(),
                r.getStatusDate(),
                r.getMessage()
        );
    }

    public List<User> getAllUsers() {
    return userRepo.findAll();
    }

}
