package com.tchyon.reviewapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerticalDto {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
}
