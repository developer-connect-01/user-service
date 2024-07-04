package com.developersconnect.userservice.repository;

import com.developersconnect.userservice.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
