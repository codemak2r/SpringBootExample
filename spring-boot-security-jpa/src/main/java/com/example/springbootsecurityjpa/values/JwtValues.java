package com.example.springbootsecurityjpa.values;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @DATA: 2020/12/3
 */
@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtValues {
    private String secret;
    private long expirationMs;
}
