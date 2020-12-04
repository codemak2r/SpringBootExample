package com.example.springbootsecurityjpa.services.impl;

import com.example.springbootsecurityjpa.Repository.RoleRepository;
import com.example.springbootsecurityjpa.Repository.UserRepository;
import com.example.springbootsecurityjpa.models.ERole;
import com.example.springbootsecurityjpa.models.Role;
import com.example.springbootsecurityjpa.models.User;
import com.example.springbootsecurityjpa.requests.RegisterRequest;
import com.example.springbootsecurityjpa.requests.SigninRequest;
import com.example.springbootsecurityjpa.responses.GeneralResponse;
import com.example.springbootsecurityjpa.responses.JwtResponse;
import com.example.springbootsecurityjpa.security.JwtUtil;
import com.example.springbootsecurityjpa.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @DATA: 2020/12/4
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        String username = registerRequest.getName();
        String email = registerRequest.getEmail();
        String password = passwordEncoder.encode(registerRequest.getPassword());
        if(userRepository.existsByUsername(username)){
            return ResponseEntity.badRequest().body(
                    new GeneralResponse<>("username exists"));
        }
        if(userRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body(
                    new GeneralResponse<>("email exists"));
        }
        User user = new User(username,email,password);
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles == null){
            Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: role is not exists"));
            roles.add(role);
        }else{
            strRoles.forEach(
                    roleName -> {
                        log.info(roleName);
                        switch (roleName) {
                            case "admin":
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                                .orElseThrow(() -> new RuntimeException("admin role is not exists"));
                                roles.add(adminRole);
                                break;
                            case "mod":
                                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                                    .orElseThrow(()->new RuntimeException("mod role is not exists"));
                                roles.add(modRole);
                                break;
                            default:
                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(()-> new RuntimeException("user role is not exists"));
                                roles.add(userRole);

                        }
                    }
            );

        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(
                new GeneralResponse<>("Register successful!")
        );
    }

    @Override
    public ResponseEntity<?> login(SigninRequest signinRequest) {
        String username = signinRequest.getUsername();
        String password = signinRequest.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generate(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> ((GrantedAuthority) item).getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));

    }
}
