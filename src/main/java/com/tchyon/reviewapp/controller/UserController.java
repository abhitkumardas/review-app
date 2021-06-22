package com.tchyon.reviewapp.controller;

import com.tchyon.reviewapp.model.User;
import com.tchyon.reviewapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.update(user));
    }

    @RequestMapping(value = "remove", method = RequestMethod.DELETE)
    public ResponseEntity removeUser(@RequestBody User user){
        return ResponseEntity.ok(userService.disableUser(user));
    }

}
