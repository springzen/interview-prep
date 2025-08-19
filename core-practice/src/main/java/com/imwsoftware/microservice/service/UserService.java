package com.imwsoftware.microservice.service;

import com.imwsoftware.microservice.api.dto.UserRequest;
import com.imwsoftware.microservice.api.dto.UserResponse;
import com.imwsoftware.microservice.model.User;
import com.imwsoftware.microservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
    }

    public UserResponse create(UserRequest req) {
        User u = new User();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setRole(req.role());
        return toResponse(repo.save(u));
    }

    public UserResponse update(Long id, UserRequest req) {
        User u = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        u.setName(req.name());
        u.setEmail(req.email());
        u.setRole(req.role());
        return toResponse(repo.save(u));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("User " + id + " not found");
        }
        repo.deleteById(id);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}