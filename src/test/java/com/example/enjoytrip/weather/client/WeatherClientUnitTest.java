package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WeatherClient 단위 테스트")
class WeatherClientUnitTest {

    private WeatherClient weatherClient;

    public WeatherClientUnitTest(){
        this.weatherClient = new WeatherClientMock();
    }

    @DisplayName("날씨 외부 API 단위 테스트")
    @Test
    void getVilageFcst() {
        // given
        int numOfRows = 1;
        int pageNo = 1;
        String dataType = "json";
        String baseDate = LocalDate.now()
                .minusDays(1)
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
        String returnMessage = weatherClient.getVilageFcst(requestDto);
        System.out.println(returnMessage);

        // then
        assertThat(returnMessage).isNotNull();
    }

}