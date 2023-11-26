package com.ivanpham.musicapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message="Email không được bỏ trống")
        @Email(message = "Email không hợp lệ")
        String email,
        @Size(min = 5, max = 20, message = "Password phải từ 5 đến 20 kí tự")
                String password
        )
{}


