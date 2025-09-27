package com.adoption.ms2requests.controller;

import com.adoption.ms2requests.dto.ApplicationRequest;
import com.adoption.ms2requests.dto.ApplicationView;
import com.adoption.ms2requests.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Tag(name = "requests")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<List<ApplicationView>> list(@PathVariable("id") UUID userId) {
        return ResponseEntity.ok(service.listByUser(userId));
    }

    @GetMapping("/{id}/requests/{applicationId}")
    public ResponseEntity<ApplicationView> detail(@PathVariable("id") UUID userId,
                                                  @PathVariable UUID applicationId) {
        return service.detail(userId, applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/prev-requests")
    public ResponseEntity<ApplicationView> create(@RequestBody ApplicationRequest req) {
        try {
            ApplicationView view = service.create(req);
            return ResponseEntity.ok(view);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
