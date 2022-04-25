package com.example.unitTests.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
public class RefreshToken {
    @Id
    private String token;
    private String userId;
    private Instant expiryDate;
}
