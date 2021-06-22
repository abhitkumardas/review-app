package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.config.Constants;
import com.tchyon.reviewapp.config.Role;
import com.tchyon.reviewapp.exception.CustomRuntimeException;
import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.Review;
import com.tchyon.reviewapp.model.User;
import com.tchyon.reviewapp.model.request.ReviewReq;
import com.tchyon.reviewapp.model.request.UserAndReviewReq;
import com.tchyon.reviewapp.model.request.UserReviewReq;
import com.tchyon.reviewapp.repository.ReviewRepository;
import com.tchyon.reviewapp.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private Utilities platformUtils;

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).get();
    }

    @Override
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    @Override
    public List<Review> findByPlatform(Platform platform) {
        return reviewRepository.findByPlatform(platform);
    }

    public void createReview(UserReviewReq userReviewReq, Long userId){
        postBulkReview(userReviewReq.getReviewReqList(), userId);
    }

    @Override
    public Review postReview(UserAndReviewReq userAndReviewReq) {

        if (userAndReviewReq.getRating()==null || userAndReviewReq.getRating()<1 || userAndReviewReq.getRating()>5){
            log.error("Please provide ratings between 1 to 5");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide ratings between 1 to 5");
        }

        if (userAndReviewReq.getUserId()==null && userAndReviewReq.getUserName()==null) {
            log.error("Please provide user information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide user information");
        }

        User user = userAndReviewReq.getUserName() != null ? userService.findByUsername(userAndReviewReq.getUserName()) : userService.findById(userAndReviewReq.getUserId());

        if (user == null) {
            log.error("Please provide valid user information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide valid user information");
        }

        if (userAndReviewReq.getPlatformId()==null && userAndReviewReq.getPlatformName()==null) {
            log.error("Please provide platform information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide platform information");
        }

        Platform platform = userAndReviewReq.getPlatformName() != null ? platformService.findByName(userAndReviewReq.getPlatformName()) : platformService.findById(userAndReviewReq.getPlatformId());
        if (platform == null) {
            log.error("Please provide valid platform information");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide valid platform information");
        }

        if (platform.getIsReleased() != null && !platform.getIsReleased()) {
            log.error("platform yet to be released");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "platform yet to be released");
        }

        List<Review> byUserAndPlatform = reviewRepository.findByUserAndPlatform(user, platform);
        if (!byUserAndPlatform.isEmpty()) {
            log.error("multiple reviews not allowed");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "multiple reviews not allowed");
        }

        Review review = new Review();
        review.setIsActive(true);
        review.setPlatform(platform);
        review.setUser(user);
        review.setComment(userAndReviewReq.getComment());
        review.setRating(calculateRatings(platform, user, userAndReviewReq.getRating()));

        review = reviewRepository.save(review);
        checkUserRoleUpdate(user);
        return review;
    }

    @Async
    private void checkUserRoleUpdate(User user) {
        if (reviewRepository.findByUser(user).size() >= 3){
            user.setRole(Role.CRITIC);
            userService.update(user);
            log.info(user.getName() + " has published 3 reviews, he is promoted to CRITIC now.");
        }
    }

    private Integer calculateRatings(Platform platform, User user, Integer ratings) {
        if (user.getRole().equals(Role.CRITIC)){
            return Constants.CRITIC_RATING_MULTIPLIER * ratings;
        }
        return ratings;
    }

    @Override
    @Transactional
    public void postBulkReview(List<ReviewReq> reviewReqs, Long userId) {
        List<Review> reviewList = new ArrayList<>();

        User user = userService.findById(userId);
        reviewReqs.forEach( x->
                {
                    Long platformId = x.getPlatformId();
                    Integer rating = x.getRating();
                    String comment = x.getComment();

                    Platform platform = platformService.findById(platformId);
                    if (platform!=null){
                        Review review = new Review();
                        review.setIsActive(true);
                        review.setRating(platformUtils.formatRatings(rating));
                        review.setComment(comment);
                        review.setPlatform(platform);
                        review.setUser(user);

                        save(review);
                        reviewList.add(review);
                    }
                }
        );
    }

    @Override
    public List<Review> findByUserId(Long userId) {
        User user = userService.findById(userId);
        return findByUser(user);
    }

    public Review save(Review review){
        review.setIsActive(review.getIsActive()==null ? true:review.getIsActive());
        return reviewRepository.save(review);
    }
    
    public Review disableReview(Review review,User user){
        boolean isAdmin = userService.isAdminUser(user);
        if (!isAdmin){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only Admin Can disable Reviews");
        }

        review.setIsActive(false);
        return save(review);
    }

    @Override
    public List<Review> getAdminSpecific(Long userId){
        boolean isAdmin = userService.isAdminUser(userId);
        return isAdmin ? findAll(): findByUserId(userId);
    }
}
