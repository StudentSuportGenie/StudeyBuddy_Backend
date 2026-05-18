package com.example.studyBuddy.Services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;

@Service
public class RateLimitingService {

    // Cache of token buckets, keyed by IP address
    private final Cache<String, Bucket> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterAccess(Duration.ofHours(1))
            .build();

    public Bucket resolveBucket(String ip) {
        return cache.get(ip, this::newBucket);
    }

    private Bucket newBucket(String ip) {
        // Limit to 20 requests per minute
        Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(20, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
