package com.example.springbootsecurityjpa.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @DATA: 2020/12/4
 */
@Getter
@Setter
public class SigninRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
