package com.example.enjoytrip.touristspot.dao;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TouristspotDao {

    List<TouristSpot> findAll(PageDto pageDto);
    void initializeDatabase();
}
