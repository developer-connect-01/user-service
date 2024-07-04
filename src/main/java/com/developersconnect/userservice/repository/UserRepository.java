package com.developersconnect.userservice.repository;

import com.developersconnect.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
}
