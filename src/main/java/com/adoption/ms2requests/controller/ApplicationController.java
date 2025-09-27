package com.adoption.ms2requests.controller;

import com.adoption.ms2requests.dto.ApplicationView;
import com.adoption.ms2requests.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Tag(name = "Applications")
public class ApplicationController {
    private final ApplicationService service;
    public ApplicationController(ApplicationService service){ this.service = service; }

    @GetMapping("/{id}/applications")
    public ResponseEntity<List<ApplicationView>> list(@PathVariable("id") UUID userId){
        return ResponseEntity.ok(service.listByUser(userId));
    }

    @GetMapping("/{id}/applications/{applicationId}")
    public ResponseEntity<ApplicationView> detail(@PathVariable("id") UUID userId,
                                                  @PathVariable UUID applicationId){
        return service.detail(userId, applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
}
