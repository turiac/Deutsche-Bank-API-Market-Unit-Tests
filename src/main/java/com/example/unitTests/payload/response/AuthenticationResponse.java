package com.example.unitTests.payload.response;

import com.example.unitTests.model.RefreshToken;
public record AuthenticationResponse(String jwt, RefreshToken refreshToken) {
}
