package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;

public interface WeatherClient {
    String getVilageFcst(WeatherRequestDto weatherRequestDto);

}
