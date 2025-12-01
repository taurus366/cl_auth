package com.aiolds.cl_auth.controller;

import com.aiolds.cl_auth.model.CustomUserDetails;
import com.aiolds.cl_auth.model.dto.AuthRequest;
import com.aiolds.cl_auth.model.dto.AuthResponse;
import com.aiolds.cl_auth.model.dto.UserInfo;
import com.aiolds.cl_auth.model.entity.UserEntity;
import com.aiolds.cl_auth.model.repository.UserRepository;
import com.aiolds.cl_auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/erp/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        UserEntity user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password or username");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    @PostMapping("/me")
    public UserInfo me(Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Optional<UserEntity> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()) {
            UserInfo userInfo = modelMapper.map(byEmail.get(), UserInfo.class);
            return userInfo;
        }
        return null;
    }
}
