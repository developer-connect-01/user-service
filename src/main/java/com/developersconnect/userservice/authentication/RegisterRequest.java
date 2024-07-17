package com.developersconnect.userservice.authentication;

import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterRequest {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final String username;
    private final String password;
}
