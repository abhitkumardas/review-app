package com.tchyon.reviewapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Platform implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Boolean isActive;

    @Column
    private Boolean isReleased;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verticalID", nullable = false, referencedColumnName = "id")
    private Vertical vertical;
}
