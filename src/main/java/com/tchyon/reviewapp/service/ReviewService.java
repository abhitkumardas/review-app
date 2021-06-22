package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.Review;
import com.tchyon.reviewapp.model.User;
import com.tchyon.reviewapp.model.request.ReviewReq;
import com.tchyon.reviewapp.model.request.UserAndReviewReq;
import com.tchyon.reviewapp.model.request.UserReviewReq;

import java.util.List;

public interface ReviewService {
    public List<Review> findAll();
    public Review findById(Long id);
    public List<Review> findByUser(User user);
    public List<Review> findByPlatform(Platform platform);

    public void createReview(UserReviewReq userReviewReq, Long userId);

    public Review postReview(UserAndReviewReq userAndReviewReq);

    public void postBulkReview(List<ReviewReq> reviewReqs, Long userId);

    public List<Review> findByUserId(Long userId);

    public List<Review> getAdminSpecific(Long userId);
}
