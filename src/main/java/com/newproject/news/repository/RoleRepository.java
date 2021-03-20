package com.newproject.news.repository;

import com.newproject.news.entity.Role;
import com.newproject.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String roleName);
}
