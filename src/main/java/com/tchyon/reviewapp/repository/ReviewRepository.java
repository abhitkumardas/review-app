package com.tchyon.reviewapp.repository;

import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.Review;
import com.tchyon.reviewapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findAll();
    public Optional<Review> findById(Long id);
    public List<Review> findByUser(User user);
    public List<Review> findByPlatform(Platform platform);
    public List<Review> findByUserAndPlatform(User user, Platform platform);
}
