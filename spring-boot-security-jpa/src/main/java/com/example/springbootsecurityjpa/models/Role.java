package com.example.springbootsecurityjpa.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @DATA: 2020/12/3
 */
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    public Role(){}

    public Role(Long id, @NotBlank @Size(max = 20) ERole name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private ERole name;
}
