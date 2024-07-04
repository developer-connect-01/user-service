package com.developersconnect.userservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDTO> results;
}


