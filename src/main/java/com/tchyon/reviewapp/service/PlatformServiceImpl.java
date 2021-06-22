package com.tchyon.reviewapp.service;

import com.tchyon.reviewapp.exception.CustomRuntimeException;
import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.Vertical;
import com.tchyon.reviewapp.model.request.PlatformDto;
import com.tchyon.reviewapp.model.request.VerticalDto;
import com.tchyon.reviewapp.repository.PlatformRepository;
import com.tchyon.reviewapp.repository.VerticalRepository;
import com.tchyon.reviewapp.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private VerticalRepository verticalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Utilities utilities;

    @Override
    public Platform findById(Long id) {
        return platformRepository.findById(id).get();
    }

    @Override
    public Platform findByName(String name) {
        return platformRepository.findByName(name);
    }

    @Override
    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    @Override
    public Platform addPlatform(PlatformDto platformDto) {
        Vertical vertical = null;
        VerticalDto verticalDto = platformDto.getVertical();

        if (platformDto.getName()==null || platformDto.getName().isEmpty()){
            log.error("please provide platform name");
            throw new CustomRuntimeException("please provide name");
        }
        if (verticalDto == null || (verticalDto.getId()==null && verticalDto.getName()==null)){
            throw new CustomRuntimeException("please provide vertical information");
        }

        if (verticalDto.getId() != null) {
             vertical = verticalRepository.findById(verticalDto.getId()).orElseThrow(() -> new CustomRuntimeException("please provide vertical information"));
        }else {
            vertical = verticalRepository.save(new Vertical(verticalDto.getName(), verticalDto.getDescription(), verticalDto.getIsActive()!=null?verticalDto.getIsActive():true));
            log.info("Vertical created >>> " + vertical);
        }

        Platform platform = new Platform();
        platform.setName(platformDto.getName());
        platform.setIsActive(platformDto.getIsActive()!=null ? platformDto.getIsActive():true);
        platform.setIsReleased(platformDto.getIsReleased()!=null ? platformDto.getIsReleased():true);
        platform.setDescription(platformDto.getDescription());
        platform.setVertical(vertical);

        return save(platform);
    }

    @Override
    public Platform addPlatformAdmin(Platform platform, Long userId) {
        boolean isAdmin = userService.isAdminUser(userId);
        // As we have not used any Authentication and Authorization System using roles to authorize Admin Permission.

        if (!isAdmin){
            new RuntimeException("Only Admin Can add Platform");
        }

        return save(platform);
    }

    @Override
    public Platform updatePlatform(Platform platform, Long userId) {
        boolean isAdmin = userService.isAdminUser(userId);
        if (!isAdmin){
            throw new RuntimeException("Only Admin Can add Platform");
        }

        return save(platform);
    }

    public Platform save(Platform platform) {
        platform.setIsActive(platform.getIsActive()==null ? true:platform.getIsActive());
        return platformRepository.save(platform);
    }


}
