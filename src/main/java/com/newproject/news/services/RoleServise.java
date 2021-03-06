package com.newproject.news.services;

import com.newproject.news.entity.Role;
import com.newproject.news.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class RoleServise {
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void delRole(long[] id) {
        for(long ids:id)
        roleRepository.deleteById(ids);
    }

    @Transactional
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role findRole(Long id) {
        return roleRepository.getById(id);
    }



}
