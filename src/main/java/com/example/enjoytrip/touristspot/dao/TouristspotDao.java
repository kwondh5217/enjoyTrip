package com.example.enjoytrip.touristspot.dao;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristCoordinateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface TouristspotDao {

    List<TouristSpot> findAll(PageDto pageDto);
    List<TouristSpot> findBySido(Map<String, Object> map);
    List<TouristSpot> findByGugun(Map<String, Object> map);
    List<TouristSpot> findByKeyword(Map<String, Object> map);
    TouristSpot findById(int touristspotId);
    List<TouristSpot> findByCoordinates(Map<String, Object> map);

}
