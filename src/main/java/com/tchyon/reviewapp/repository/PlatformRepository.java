package com.tchyon.reviewapp.repository;

import com.tchyon.reviewapp.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    public List<Platform> findAll();

    public Optional<Platform> findById(Long userId);

    public Platform findByName(String name);
}
