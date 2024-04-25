package com.example.enjoytrip.touristspot.dao;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.dto.TouristCoordinateDto;
import com.example.enjoytrip.touristspot.dto.TouristspotDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class TouristspotDaoTest {

    @Autowired
    TouristspotDao touristspotDao;

    @Test
    void DI(){
        assertThat(touristspotDao).isNotNull();
    }

    @DisplayName("여행지 정보를 5개만 조회한다")
    @Test
    void findAll_page() {
        // given
        PageDto pageDto = new PageDto(1, 5);

        // when
        List<TouristSpot> all = touristspotDao.findAll(pageDto);

        // then
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(5);
    }

    @DisplayName("여행지 정보를 시도코드로 5개만 조회한다")
    @Test
    void findBySidoCode_page() {
        // given
        Map<String, Object> map = new HashMap<>();
        int sidoCode = 4;
        PageDto pageDto = new PageDto(1, 5);
        map.put("sidoCode", sidoCode);
        map.put("pageDto", pageDto);

        // when
        List<TouristSpot> all = touristspotDao.findBySido(map);

        // then
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(5);
    }

    @DisplayName("여행지 정보를 구군코드로 5개만 조회한다")
    @Test
    void findByGugunCode_page() {
        // given
        Map<String, Object> map = new HashMap<>();
        int gugunCode = 4;
        PageDto pageDto = new PageDto(1, 5);
        map.put("gugunCode", gugunCode);
        map.put("pageDto", pageDto);

        // when
        List<TouristSpot> all = touristspotDao.findByGugun(map);

        // then
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(5);
    }

    @DisplayName("여행지 정보를 키워드로 3개만 조회한다")
    @Test
    void findByKeyword_page() {
        // given
        Map<String, Object> map = new HashMap<>();
        String keyword = "대구";
        PageDto pageDto = new PageDto(1, 3);
        map.put("keyword", keyword);
        map.put("pageDto", pageDto);

        // when
        List<TouristSpot> all = touristspotDao.findByKeyword(map);

        // then
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(3);
    }

    @DisplayName("여행지 정보를 식별자로 조회한다")
    @Test
    void findById() {
        // given
        int id = 125266;
        String title = "국립 청태산자연휴양림";

        // when
        TouristSpot touristSpot = touristspotDao.findById(id);

        // then
        assertThat(touristSpot).isNotNull();
        assertThat(touristSpot.getTouristspotTitle()).isEqualTo(title);
    }

    @DisplayName("위도 경도 범위 내의 여행지 정보 조회하기")
    @Test
    void getTouristspotByLocationInfo() {
        // given
        Map<String, Object> map = new HashMap<>();
        TouristCoordinateDto touristCoordinateDto = TouristCoordinateDto.builder()
                .northLatitude(BigDecimal.valueOf(35.54851698585924))
                .southLatitude(BigDecimal.valueOf(33.86605388558357))
                .eastLongitude(BigDecimal.valueOf(132.27023703996352))
                .westLongitude(BigDecimal.valueOf(120.30370775572416))
                .build();
        PageDto pageDto = new PageDto(1, 3);
        map.put("pageDto", pageDto);
        map.put("touristCoordinateDto", touristCoordinateDto);


        // when
        List<TouristSpot> byCoordinates = touristspotDao.findByCoordinates(map);

        // then
        assertThat(byCoordinates).isNotEmpty();
    }

}