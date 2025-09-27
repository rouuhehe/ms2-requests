package com.adoption.ms2requests.repo;

import com.adoption.ms2requests.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepo {
    private final Map<UUID, User> data = new ConcurrentHashMap<>();
    public Optional<User> findById(UUID id){ return Optional.ofNullable(data.get(id)); }
    public void save(User u){ data.put(u.getId(), u); }
}
