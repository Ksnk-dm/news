package com.newproject.news.repository;

import com.newproject.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.nio.channels.UnresolvedAddressException;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String username);
    User findUserByEmail(String email);
    User findByEmail(String email);
    User findByResetPasswordToken(String token);
}
