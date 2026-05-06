package com.fintech.user_services.Service;


import com.fintech.user_services.dto.*;
import com.fintech.user_services.Entity.UserProfile;
import com.fintech.user_services.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;

    public UserResponse createOrUpdateUser(String email, UserRequest request) {
        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElse(UserProfile.builder().email(email).build());

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());

        userProfileRepository.save(profile);
        return mapToResponse(profile);
    }

    public UserResponse getUserByEmail(String email) {
        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(profile);
    }

    private UserResponse mapToResponse(UserProfile p) {
        return UserResponse.builder()
                .id(p.getId())
                .email(p.getEmail())
                .fullName(p.getFullName())
                .phone(p.getPhone())
                .address(p.getAddress())
                .createdAt(p.getCreatedAt())
                .build();
    }
}