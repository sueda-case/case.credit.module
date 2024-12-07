package com.example.auth.module.model.repository;

import com.example.auth.module.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
}
