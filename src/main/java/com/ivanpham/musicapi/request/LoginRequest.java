package com.ivanpham.musicapi.request;

//import javax.validation.constraints.*;
import jakarta.validation.constraints.*;
public record LoginRequest(
        @NotBlank(message="Email không được bỏ trống")
        @Email(message = "Email không hợp lệ")
        String email,
        @Size(min = 5, max = 20, message = "Password phải từ 5 đến 20 kí tự")
                String password

        )
{}


