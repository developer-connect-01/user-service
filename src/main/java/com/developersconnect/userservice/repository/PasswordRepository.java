package com.developersconnect.userservice.repository;

import com.developersconnect.userservice.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
