package com.developersconnect.userservice.repository;

import com.developersconnect.userservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
