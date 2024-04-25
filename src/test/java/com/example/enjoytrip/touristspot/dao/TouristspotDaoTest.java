package com.example.enjoytrip.touristspot.dao;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristspotDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@MybatisTest
class TouristspotDaoTest {

    @Autowired
    TouristspotDao touristspotDao;

    @DisplayName("test 초기 설정")
    @Test
    void initData(){
        touristspotDao.initializeDatabase();
    }

//    @DisplayName("여행지 정보를 10개만 조회한다")
//    @Test
//    void findAll_page() {
//        // given
//        PageDto pageDto = new PageDto(0, 10);
//
//        // when
//        List<TouristSpot> all = touristspotDao.findAll(pageDto);
//
//        // then
//        assertThat(all).isNotEmpty();
//        assertThat(all.size()).isEqualTo(10);
//
//    }

}