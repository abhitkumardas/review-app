package com.tchyon.reviewapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platformID", nullable = false, referencedColumnName = "id")
    private Platform platform;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String comment;

    @Column
    @JsonIgnore
    private Boolean isActive;

}
