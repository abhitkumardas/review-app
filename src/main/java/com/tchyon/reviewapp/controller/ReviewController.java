package com.tchyon.reviewapp.controller;

import com.tchyon.reviewapp.model.request.ReviewReq;
import com.tchyon.reviewapp.model.request.UserAndReviewReq;
import com.tchyon.reviewapp.model.request.UserReviewReq;
import com.tchyon.reviewapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity postReview(@RequestBody UserAndReviewReq userAndReviewReq){
        return ResponseEntity.ok(reviewService.postReview(userAndReviewReq));
    }

    @PostMapping("/bulk")
    public ResponseEntity createReview(@RequestBody UserReviewReq userReviewReq, @RequestHeader Long userId){
        reviewService.createReview(userReviewReq, userId);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/bulk/v1")
    public ResponseEntity postBulkReview(@RequestBody List<ReviewReq> reviewReqs, @RequestHeader Long userId){
        reviewService.postBulkReview(reviewReqs, userId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
    public  ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping("/all")
    public  ResponseEntity getAll(@RequestHeader Long userId){
        return ResponseEntity.ok(reviewService.getAdminSpecific(userId));
    }


}
