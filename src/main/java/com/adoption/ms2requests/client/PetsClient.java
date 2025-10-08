package com.adoption.ms2requests.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class PetsClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean petExists(UUID petId) {
        try {
            String BASE_URL = "http://ms1-pets:8000";
            ResponseEntity<String> response =
                restTemplate.getForEntity(BASE_URL + "/ms1/pets/" + petId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            return false;
        }
    }
}
