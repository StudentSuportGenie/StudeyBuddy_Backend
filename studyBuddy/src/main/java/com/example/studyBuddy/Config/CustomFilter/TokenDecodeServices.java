package com.example.studyBuddy.Config.CustomFilter;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenDecodeServices {

    private final JwtDecoder jwtDecoder;

    public TokenDecodeServices() {
        this.jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri("https://studtsupportdemo.b2clogin.com/studtsupportdemo.onmicrosoft.com/b2c_1_study/discovery/v2.0/keys")
                .build();
    }

    public List<String> getEmail(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsStringList("emails");
    }

    public String getCountry(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString("country");
    }

    public String getJobTitle(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString("jobTitle");
    }
}
