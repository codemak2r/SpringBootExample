package com.example.springbootsecurityjpa.Repository;

import com.example.springbootsecurityjpa.models.ERole;
import com.example.springbootsecurityjpa.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @DATA: 2020/12/4
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName(ERole name);
}
