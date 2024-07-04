package com.developersconnect.userservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String gender;
    private String title;
    private String firstName;
    private String lastName;
    private LocationDTO locationDTO;
    private String email;
    private String username;
    private PasswordDTO passwordDTO;
    private Date dob;
    private Date registered;
    private String phone;
    private String cell;
    private PictureDTO pictureDTO;
    private String nat;
}