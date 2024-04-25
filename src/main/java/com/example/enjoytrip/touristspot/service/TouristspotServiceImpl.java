package com.example.enjoytrip.touristspot.service;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.dao.TouristspotDao;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristCoordinateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
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
        Map<String, Object> map = new HashMap<>();
        map.put("sidoCode", sidoCode);
        map.put("pageDto", pageDto);
        return touristspotDao.findBySido(map);
    }

    @Override
    public List<TouristSpot> findByGugun(int gugunCode, PageDto pageDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("gugunCode", gugunCode);
        map.put("pageDto", pageDto);
        return touristspotDao.findByGugun(map);
    }

    @Override
    public List<TouristSpot> findByKeyword(String keyword, PageDto pageDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("pageDto", pageDto);
        return touristspotDao.findByKeyword(map);
    }

    @Override
    public TouristSpot findById(int touristspotId) {
        return touristspotDao.findById(touristspotId);
    }

    @Override
    public List<TouristSpot> findByCoordinates(TouristCoordinateDto touristCoordinateDto, PageDto pageDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageDto", pageDto);
        map.put("touristCoordinateDto", touristCoordinateDto);
        return touristspotDao.findByCoordinates(map);
    }
}
