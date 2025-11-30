package com.aiolds.cl_auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String img;

}
