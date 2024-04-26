package com.example.enjoytrip.weather.client;

import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class WeatherClientImpl implements WeatherClient{

    @Value("${weather.baseUrl}")
    private String url;

    @Override
    public String getVilageFcst(WeatherRequestDto requestDto){
        RestClient client = RestClient.builder()
                .baseUrl(url)
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("numOfRows", requestDto.getNumOfRows())
                        .queryParam("pageNo", requestDto.getPageNo())
                        .queryParam("dataType", requestDto.getDataType())
                        .queryParam("base_date", requestDto.getBaseDate())
                        .queryParam("base_time", requestDto.getBaseTime())
                        .queryParam("nx", requestDto.getNx())
                        .queryParam("ny", requestDto.getNy())
                        .build())
                .retrieve()
                .body(String.class);
    }
}
