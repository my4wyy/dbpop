package com.maisapires.todosimple.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String role;
}
