package com.imwsoftware.microservice.service;

import com.imwsoftware.microservice.model.CreateDTO;
import com.imwsoftware.microservice.model.UrlEntry;
import com.imwsoftware.microservice.url.UrlShortener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {
    private final UrlShortener urlShortener;

    public UrlShortenerService(UrlShortener urlShortener) {
        this.urlShortener = new UrlShortener();
    }

    public UrlEntry shorten(CreateDTO rec) {
        return this.urlShortener.shorten(rec);
    }

    public Optional<UrlEntry> expand(String code) {
        return this.urlShortener.expand(code);
    }
}
