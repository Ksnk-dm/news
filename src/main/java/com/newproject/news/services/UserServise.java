package com.newproject.news.services;

import com.newproject.news.entity.Role;
import com.newproject.news.entity.User;
import com.newproject.news.repository.RoleRepository;
import com.newproject.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@PropertySource("classpath:site.properties")
public class UserServise implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${site.newUserRole}")
    private String idNewUser;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    public User findUserByUsername(String username) {
        User userFromDb = userRepository.findUserByUserName(username);
        return userFromDb;
    }

    @Transactional(readOnly=true)
    public Optional<User> findUser(long id) {
        return userRepository.findById(id);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        System.out.println("!!!!!!!!"+ idNewUser);
        User userFromDB = userRepository.findUserByUserName(user.getUsername());
        User userFromDBEmail = userRepository.findByEmail(user.getEmail());
        Role role = roleRepository.getById(Long.valueOf(idNewUser));
        if (userFromDB != null||userFromDBEmail!=null) {
            return false;
        }
        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    @Transactional
    public void deleteUser(long[] userId) {
            for (long id : userId)
            userRepository.deleteById(id);

    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Почту не найдено: " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void updateEmail(User user, String newEmail){
        user.setEmail(newEmail);
        userRepository.save(user);
    }


    public boolean addRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        userRepository.save(user);
        return true;
    }

    public boolean delRoleToUser(User user, Role role) {
    Set<Role> roles =user.getRoles();
    roles.remove(role);
    user.setRoles(roles);
        userRepository.save(user);
        return true;
    }


}

