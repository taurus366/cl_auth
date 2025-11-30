package com.aiolds.cl_auth.model.repository;

import com.aiolds.cl_auth.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
