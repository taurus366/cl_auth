package com.aiolds.cl_auth.security;

import com.aiolds.cl_auth.model.CustomUserDetails;
import com.aiolds.cl_auth.model.entity.UserEntity;
import com.aiolds.cl_auth.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;


import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");

        if(authToken != null && authToken.startsWith("Bearer ")) {
            String token = authToken.substring(7);

            try {
                String email = jwtService.extractEmail(token);
                UserEntity user = userRepository.findByEmail(email).orElse(null);

                if(user != null && !jwtService.isTokenExpired(token)) {

                    CustomUserDetails customUserDetails = new CustomUserDetails(
                            user.getId(),
                            user.getEmail(),
                            user.getFirstName(),
                            user.getMiddleName(),
                            user.getLastName(),
                            user.getUsername(),
                            List.of(new SimpleGrantedAuthority("ROLE_" + "USER")),
                            user.getPhone(),
                            user.getImg()
                    );
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(customUserDetails, null, List.of(new SimpleGrantedAuthority("ROLE_" + "USER")));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                System.out.println("Invalid JWT Token" + e.getMessage());
            }
        }
        filterChain.doFilter(request,response);

    }
}
