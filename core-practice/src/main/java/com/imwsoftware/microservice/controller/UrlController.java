package com.imwsoftware.microservice.controller;

import com.imwsoftware.microservice.model.CreateDTO;
import com.imwsoftware.microservice.model.UrlEntry;
import com.imwsoftware.microservice.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, Object>> shorten(@RequestBody CreateDTO req) {
        UrlEntry e = service.shorten(req);
        return ResponseEntity.ok(Map.of("code", e.code(), "longUrl", e.longUrl()));
    }

    @GetMapping("/expand/{code}")
    public ResponseEntity<?> expand(@PathVariable String code) {
        return service.expand(code)
                .<ResponseEntity<?>>map(e -> ResponseEntity.ok(Map.of("code", e.code(), "longUrl", e.longUrl())))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Not found")));
    }
}
