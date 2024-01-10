package com.hackaboss.agenciaTurismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String name;
    private String lastName;
    private String passport;
    private int age;

}
