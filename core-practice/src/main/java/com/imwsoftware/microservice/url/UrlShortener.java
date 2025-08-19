package com.imwsoftware.microservice.url;

import com.imwsoftware.microservice.model.CreateDTO;
import com.imwsoftware.microservice.model.UrlEntry;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UrlShortener {
    // Shared in-memory store (OK for interviews). In real code: inject a repo/cache.
    private static final Map<String, UrlEntry> BY_CODE = new ConcurrentHashMap<>();     // code -> entry
    private static final Map<String, String> BY_LONG = new ConcurrentHashMap<>();       // normalized longUrl -> code

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RNG = new SecureRandom();
    private static final int CODE_LEN = 7;

    public UrlEntry shorten(CreateDTO req) {
        String longUrl = normalize(req.url());
        if (!isValidHttpUrl(longUrl)) {
            throw new IllegalArgumentException("Invalid URL");
        }

        // If custom provided, prefer it; else reuse existing or create new
        String code = (req.customCode() != null && !req.customCode().isBlank())
                ? req.customCode().trim()
                : BY_LONG.computeIfAbsent(longUrl, k -> newUniqueCode());

        // If custom code collides with a different URL, reject
        UrlEntry existing = BY_CODE.get(code);
        if (req.customCode() != null && existing != null && !existing.longUrl().equals(longUrl)) {
            throw new IllegalStateException("Code already in use");
        }

        // Upsert
        UrlEntry entry = new UrlEntry(code, longUrl, Instant.now());
        BY_CODE.putIfAbsent(code, entry);
        // Keep BY_LONG in sync (in case custom slug reused existing URL)
        BY_LONG.putIfAbsent(longUrl, code);

        return BY_CODE.get(code);
    }

    public Optional<UrlEntry> expand(String code) {
        return Optional.ofNullable(BY_CODE.get(code));
    }

    // ---------- helpers ----------
    private static String newUniqueCode() {
        String code;
        do {
            code = newCode();
        } while (BY_CODE.containsKey(code));
        return code;
    }

    private static String newCode() {
        StringBuilder sb = new StringBuilder(CODE_LEN);
        for (int i = 0; i < CODE_LEN; i++) sb.append(ALPHABET.charAt(RNG.nextInt(ALPHABET.length())));
        return sb.toString();
    }

    private static String normalize(String url) {
        return url == null ? "" : url.trim();
    }

    private static boolean isValidHttpUrl(String url) {
        try {
            URI u = URI.create(url);
            return (u.getScheme() != null && (u.getScheme().equalsIgnoreCase("http") || u.getScheme().equalsIgnoreCase("https"))
                    && u.getHost() != null);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
