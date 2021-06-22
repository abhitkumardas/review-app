package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.request.PlatformDto;

import java.util.List;

public interface PlatformService {
    public Platform findById(Long id);

    public Platform findByName(String name);

    public List<Platform> findAll();

    public Platform addPlatform(PlatformDto platformDto);

    public Platform addPlatformAdmin(Platform platform, Long userId);

    public Platform updatePlatform(Platform platform, Long userId);
}
