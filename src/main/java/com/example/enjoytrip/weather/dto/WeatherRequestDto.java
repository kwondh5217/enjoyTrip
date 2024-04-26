package com.example.enjoytrip.weather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WeatherRequestDto {
    private int numOfRows;
    private int pageNo;
    private String dataType;
    private String baseDate;
    private String baseTime;
    @NotNull
    private Integer nx;
    @NotNull
    private Integer ny;
}