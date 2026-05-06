package com.fintech.user_services.controller;


import com.fintech.user_services.dto.*;
import com.fintech.user_services.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> createOrUpdate(
            Authentication auth,
            @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createOrUpdateUser(auth.getName(), request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication auth) {
        return ResponseEntity.ok(userService.getUserByEmail(auth.getName()));
    }
}