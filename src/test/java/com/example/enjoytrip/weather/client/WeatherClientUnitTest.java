package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherClientUnitTest {

    private WeatherClient weatherClient;

    public WeatherClientUnitTest(){
        this.weatherClient = new WeatherClientMock();
    }

    @Test
    void getVilageFcst() {
        // given
        int numOfRows = 1;
        int pageNo = 1;
        String dataType = "json";
        String baseDate = "20240425";
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