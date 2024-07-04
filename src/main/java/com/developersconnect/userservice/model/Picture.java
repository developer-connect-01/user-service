package com.developersconnect.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String large;
    private String medium;
    private String thumbnail;

    @OneToOne(mappedBy = "picture")
    private AppUser user;
}
