package com.developersconnect.userservice.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private String latitude;
    private String longitude;


    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="street_id", referencedColumnName = "id")
    private Street street;

    @OneToOne(mappedBy = "location")
    private User user;

}
