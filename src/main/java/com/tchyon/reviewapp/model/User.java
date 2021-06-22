package com.tchyon.reviewapp.model;

import com.tchyon.reviewapp.config.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Boolean isActive;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;


    public User(String username, String name, String mobile, String email) {
        this.username = username;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }
}
