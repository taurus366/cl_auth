package com.aiolds.cl_auth.model.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
