package com.example.springbootsecurityjpa.Repository;

import com.example.springbootsecurityjpa.models.ERole;
import com.example.springbootsecurityjpa.models.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @DATA: 2020/12/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RoleTest {
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testType(){
        Role r = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                ()-> new RuntimeException("eee")
        );


    }
}
