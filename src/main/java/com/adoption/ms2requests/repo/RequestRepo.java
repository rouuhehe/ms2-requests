package com.adoption.ms2requests.repo;

import com.adoption.ms2requests.model.Request;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RequestRepo {
    private final Map<UUID, Request> data = new ConcurrentHashMap<>();

    /*
    --- DEMO DATA (remove after testing) ---
    public static final UUID DEMO_USER_ID = UUID.randomUUID();
    public static final UUID DEMO_APPLICATION_ID = UUID.randomUUID();

    public RequestRepo() {
        Request r = new Request();
        r.setId(DEMO_APPLICATION_ID);
        r.setUserId(DEMO_USER_ID);
        r.setPetId(UUID.randomUUID());
        r.setRequestDate(Instant.now());
        r.setStatus("pending");
        r.setStatusDate(null);
        r.setMessage("Your request is being processed.");
        data.put(r.getId(), r);

        System.out.println(">>> DEMO USER ID:        " + DEMO_USER_ID);
        System.out.println(">>> DEMO APPLICATION ID: " + DEMO_APPLICATION_ID);
    }
     --- END DEMO ---
     */

    public Optional<Request> findById(UUID id){ return Optional.ofNullable(data.get(id)); }
    public List<Request> findByUserId(UUID userId){
        return data.values().stream().filter(r -> r.getUserId().equals(userId)).toList();
    }
    public void save(Request r){ data.put(r.getId(), r); }
}
