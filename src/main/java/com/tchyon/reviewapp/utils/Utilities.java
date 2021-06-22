package com.tchyon.reviewapp.utils;

import com.tchyon.reviewapp.config.Constants;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utilities {

    public int formatRatings(Integer ratings){
        int maxRat = Constants.MAX_RATING;

        ratings = (ratings==null || ratings<0) ? 0 : (ratings>maxRat) ? maxRat : ratings ;
        return ratings;
    }

    public String guidGenerator(String key){
        String guid = UUID.nameUUIDFromBytes(key.getBytes()).toString();
        return guid;
    }

    public String guidGenerator(Long key){
        byte[] bytes =new byte[key.byteValue()];
        String guid = UUID.nameUUIDFromBytes(bytes).toString();
        return guid;
    }
}
