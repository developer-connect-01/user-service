package com.developersconnect.userservice.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private StreetDTO streetDTO;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private String latitude;
    private String longitude;
}
