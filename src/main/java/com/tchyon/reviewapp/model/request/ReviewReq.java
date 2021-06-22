package com.tchyon.reviewapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReq {
    private Long platformId;
    private Integer rating;
    private String comment;
}
