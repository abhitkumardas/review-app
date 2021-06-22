package com.tchyon.reviewapp.repository;

import com.tchyon.reviewapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAll();

    public Optional<User> findById(Long userId);

    public User findByUsername(String username);

    public User findByMobile(String mobile);

    public User findByEmail(String email);
}
