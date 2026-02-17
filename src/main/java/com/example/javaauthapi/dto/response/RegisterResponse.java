package com.example.javaauthapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse<T> {
    private String message;
    private T data;
}
