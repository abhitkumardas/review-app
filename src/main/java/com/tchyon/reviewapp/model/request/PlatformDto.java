package com.tchyon.reviewapp.model.request;

import com.tchyon.reviewapp.model.Vertical;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformDto {
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isReleased;
    private VerticalDto vertical;
}
