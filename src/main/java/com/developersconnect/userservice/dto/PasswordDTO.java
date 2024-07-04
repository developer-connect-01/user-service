package com.developersconnect.userservice.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private Long id;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
}
