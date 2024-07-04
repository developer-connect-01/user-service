package com.developersconnect.userservice.dto;

import lombok.Data;

@Data
public class PictureDTO {
    private Long id;
    private String large;
    private String medium;
    private String thumbnail;
}
