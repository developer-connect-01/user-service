package com.developersconnect.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private String name;

    @OneToMany(mappedBy="street", cascade= CascadeType.ALL)
    private List<Location> locations = new ArrayList<Location>();
}
