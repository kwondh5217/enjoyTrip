package com.example.enjoytrip.touristspot.service;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristCoordinateDto;

import java.util.List;

public interface TouristspotService {
    List<TouristSpot> findAll(PageDto pageDto);
    List<TouristSpot> findBySido(int sidoCode, PageDto pageDto);
    List<TouristSpot> findByGugun(int gugunCode, PageDto pageDto);
    List<TouristSpot> findByKeyword(String keyword, PageDto pageDto);
    TouristSpot findById(int touristspotId);
    List<TouristSpot> findByCoordinates(TouristCoordinateDto touristCoordinateDto, PageDto pageDto);

}
