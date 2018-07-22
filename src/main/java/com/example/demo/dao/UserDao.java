package com.example.demo.dao;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByName(String name);
    User findById(Long id);
    User findByNameAndGender(String name, Boolean gender);

}
