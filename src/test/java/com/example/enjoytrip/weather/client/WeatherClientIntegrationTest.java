package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WeatherClient 통합 테스트")
@SpringBootTest
class WeatherClientIntegrationTest {

    @Autowired
    WeatherClient weatherClient;

    @DisplayName("날씨 외부 API를 실제 호출한다")
    @Test
    void getVilageFcst(){
        // given
        int numOfRows = 50;
        int pageNo = 1;
        String dataType = "json";
        String baseDate = LocalDate.now()
//                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = "0500";
        int nx = 55;
        int ny = 127;

        WeatherRequestDto requestDto = WeatherRequestDto.builder()
                .numOfRows(numOfRows)
                .pageNo(pageNo)
                .dataType(dataType)
                .baseDate(baseDate)
                .baseTime(baseTime)
                .nx(nx)
                .ny(ny)
                .build();
        // when
        String response = weatherClient.getVilageFcst(requestDto);
        System.out.println(response);
        // then
        assertThat(response).isNotNull();
    }

}