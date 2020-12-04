package com.example.springbootsecurityjpa.values;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @DATA: 2020/12/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataSourceValuesTest {
    @Value("${spring.datasource.url}")
    private String url;
    @Test
    public void configurationTest(){
    }
}
