package com.adoption.ms2requests.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adoption.ms2requests.dto.ApplicationRequest;
import com.adoption.ms2requests.dto.ApplicationView;
import com.adoption.ms2requests.model.User;
import com.adoption.ms2requests.service.ApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "requests")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping("/ms2/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/ms2/{id}/requests")
    public ResponseEntity<List<ApplicationView>> list(@PathVariable("id") UUID userId) {
        return ResponseEntity.ok(service.listByUser(userId));
    }

    @GetMapping("/ms2/{id}/requests/{applicationId}")
    public ResponseEntity<ApplicationView> detail(@PathVariable("id") UUID userId,
                                                  @PathVariable UUID applicationId) {
        return service.detail(userId, applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/ms2/requests")
    public ResponseEntity<ApplicationView> create(@RequestBody ApplicationRequest req) {
        try {
            ApplicationView view = service.create(req);
            return ResponseEntity.ok(view);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
