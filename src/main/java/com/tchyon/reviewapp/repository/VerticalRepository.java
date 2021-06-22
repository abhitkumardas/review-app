package com.tchyon.reviewapp.repository;

import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.Vertical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerticalRepository extends JpaRepository<Vertical, Long> {

    public List<Vertical> findAll();

    public Optional<Vertical> findById(Long userId);
}
