package com.example.springbootsecurityjpa.services.impl;

import com.example.springbootsecurityjpa.Repository.UserRepository;
import com.example.springbootsecurityjpa.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @DATA: 2020/12/4
 */
@Service
public class UserDetailsServiceIml implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional  // 数据库事务安全。 如果出现异常不会执行任何数据库操作
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        return UserDetailsImpl.build(user);
    }
}
