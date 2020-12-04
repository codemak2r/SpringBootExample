package com.example.springbootsecurityjpa.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @DATA: 2020/12/3
 */
@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    private String password2;

    private Set<String> role;
}
