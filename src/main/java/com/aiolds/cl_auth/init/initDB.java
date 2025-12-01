package com.aiolds.cl_auth.init;

import com.aiolds.cl_auth.model.entity.UserEntity;
import com.aiolds.cl_auth.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class initDB implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        initAdmin();
    }

    private void initAdmin() {
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("aiolds"));
        admin.setEmail("taurus.ali47@gmail.com");
        userRepository.save(admin);
    }
}
