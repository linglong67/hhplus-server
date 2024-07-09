package io.hhplus.server.presentation.token.controller;

import io.hhplus.server.presentation.token.dto.TokenRequest;
import io.hhplus.server.presentation.token.dto.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @PostMapping
    public TokenResponse generateToken(@RequestBody TokenRequest request) {
        return new TokenResponse(1L, "test token", "WAITING", 1_000L);
    }
}
