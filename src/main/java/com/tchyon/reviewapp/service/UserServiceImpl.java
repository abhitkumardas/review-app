package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.config.Role;
import com.tchyon.reviewapp.model.User;
import com.tchyon.reviewapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long userId){
        User user = userRepository.findById(userId).get();
        if (user==null){
            log.error("User Not Exists with Id "+userId);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        User user1 = findByUsername(user.getUsername());
        if (user1!=null){
            throw new RuntimeException("User Already Exists with Username "+ user.getUsername());
        }
        user1=findByMobile(user.getMobile());
        if (user1!=null){
            throw new RuntimeException("User Already Exists with Mobile "+ user.getMobile());
        }
        user1=findByEmail(user.getEmail());
        if (user1!=null){
            throw new RuntimeException("User Already Exists with Email "+ user.getEmail());
        }

        user.setRole(user.getRole()!=null ? user.getRole() : Role.VIEWER);
        user.setIsActive(user.getIsActive()==null ? true:user.getIsActive());

        return userRepository.save(user);
    }

    public User update(User user) {
        //check if user is there or not
        User user1 = findById(user.getId());
        if (user1 == null) {
            log.error("Unable to find User by id ... Creating new User");
        }
        return userRepository.save(user);
    }

    //We are not deleting the User we are just disabling them
    public User disableUser(User user) {
        //check wheather user exists
        User user1 = findById(user.getId());
        user.setIsActive(false);
        return userRepository.save(user);
    }

    @Override
    public boolean isAdminUser(User user) {
        Role role = user.getRole();
        if (role.equals(Role.ADMIN)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdminUser(Long userId) {
        User user = findById(userId);
        return isAdminUser(user);
    }
}
