package com.fintech.user_services.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    private String fullName;
    private String phone;
    private String address;
}