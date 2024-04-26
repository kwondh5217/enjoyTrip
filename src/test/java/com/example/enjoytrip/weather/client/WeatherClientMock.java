package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class WeatherClientMock implements WeatherClient {

    @Override
    public String getVilageFcst(WeatherRequestDto weatherRequestDto) {
        return "success";
    }
}
