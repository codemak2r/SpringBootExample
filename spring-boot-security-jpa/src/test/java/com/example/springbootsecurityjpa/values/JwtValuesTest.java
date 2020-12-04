package com.example.springbootsecurityjpa.values;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @DATA: 2020/12/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtValuesTest {
    @Autowired
    JwtValues jwtValues;

    @Test
    public void loadConfigurationTest(){
        assertEquals("SecretKey", jwtValues.getSecret());
        assertTrue(86400000 == jwtValues.getExpirationMs());
    }
}
