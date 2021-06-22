package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.model.User;

import java.util.List;

public interface UserService {

    public User findById(Long userId);
    public User findByUsername(String username);
    public User findByMobile(String mobile);
    public User findByEmail(String email);
    public List<User> findAll();
    public User save(User user);
    public User update(User user);
    public User disableUser(User user);

    public boolean isAdminUser(User user);
    public boolean isAdminUser(Long userId);
}
