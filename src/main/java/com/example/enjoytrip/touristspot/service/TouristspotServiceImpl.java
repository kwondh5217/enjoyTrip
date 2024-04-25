package com.example.enjoytrip.touristspot.service;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.dao.TouristspotDao;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristCoordinateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TouristspotServiceImpl implements TouristspotService{

    private final TouristspotDao touristspotDao;

    @Override
    public List<TouristSpot> findAll(PageDto pageDto) {
        return touristspotDao.findAll(pageDto);
    }

    @Override
    public List<TouristSpot> findBySido(int sidoCode, PageDto pageDto) {
        return null;
    }

    @Override
    public List<TouristSpot> findByGugun(int gugunCode, PageDto pageDto) {
        return null;
    }

    @Override
    public List<TouristSpot> findByKeyword(String keyword, PageDto pageDto) {
        return null;
    }

    @Override
    public TouristSpot findById(int touristspotId) {
        return null;
    }

    @Override
    public List<TouristSpot> findByCoordinates(TouristCoordinateDto touristCoordinateDto, PageDto pageDto) {
        return null;
    }
}
