package com.imwsoftware.microservice.controller;

import com.imwsoftware.microservice.model.CreateDTO;
import com.imwsoftware.microservice.model.UrlDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UrlShortController {

    @PostMapping("/shorten")
    public ResponseEntity<UrlDTO> shorten(@RequestBody CreateDTO req) {
        return null;
    }

}