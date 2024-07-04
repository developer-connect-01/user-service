package com.developersconnect.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;

    @OneToOne(mappedBy = "password")
    private AppUser user;
}
