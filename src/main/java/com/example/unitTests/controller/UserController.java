package com.example.unitTests.controller;

import com.example.unitTests.exception.JWTException;
import com.example.unitTests.exception.TokenRefreshException;
import com.example.unitTests.model.RefreshToken;
import com.example.unitTests.model.User;
import com.example.unitTests.payload.request.AuthenticationRequest;
import com.example.unitTests.payload.request.LogOutRequest;
import com.example.unitTests.payload.request.TokenRefreshRequest;
import com.example.unitTests.payload.response.AuthenticationResponse;
import com.example.unitTests.payload.response.MessageResponse;
import com.example.unitTests.payload.response.TokenRefreshResponse;
import com.example.unitTests.repository.UserRepository;
import com.example.unitTests.service.RefreshTokenService;
import com.example.unitTests.service.UserDetailsServiceImpl;
import com.example.unitTests.service.UserService;
import com.example.unitTests.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, refreshTokenService.createRefreshToken(authenticationRequest.getEmail())));
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        if (userRepository.findUserByEmail(authenticationRequest.getEmail()) != null)
            return "The user already exists.";

        User user = new User();
        user.setUserID(UUID.randomUUID().toString());
        user.setEmail(authenticationRequest.getEmail());
        user.setUsername(authenticationRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(authenticationRequest.getPassword()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);
        return "The user was created.";
    }

    @GetMapping(value = "/bps/data/user/{JWT}")
    public User getUserFromJWTPV(@PathVariable String JWT) throws JWTException {
        return getUserFromJWT(JWT);
    }

    @GetMapping(value = "/data/user")
    public User getUserFromJWTQP(@RequestParam String JWT) throws JWTException {
        return getUserFromJWT(JWT);
    }

    public User getUserFromJWT(String JWT) throws JWTException {
        String email = jwtUtil.extractEmail(JWT);

        if (email != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByEmail(email);
            if (jwtUtil.validateToken(JWT, userDetails)) {
                return userRepository.findUserByEmail(email);
            }
        }
        return null;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(user -> {
                    String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user));
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
