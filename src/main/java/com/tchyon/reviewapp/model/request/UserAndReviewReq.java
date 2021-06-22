package com.tchyon.reviewapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndReviewReq {
    private String userName;
    private Long userId;

    private Long platformId;
    private String platformName;
    private Integer rating;
    private String comment;
}
