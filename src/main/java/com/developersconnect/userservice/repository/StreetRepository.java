package com.developersconnect.userservice.repository;

import com.developersconnect.userservice.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Long> {
}
