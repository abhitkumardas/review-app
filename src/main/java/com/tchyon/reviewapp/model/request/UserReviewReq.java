package com.tchyon.reviewapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewReq {
    List<ReviewReq> reviewReqList;
}
