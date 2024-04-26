package com.example.enjoytrip.weather;

import com.example.enjoytrip.exception.CustomException;
import com.example.enjoytrip.exception.ErrorCode;
import com.example.enjoytrip.weather.client.WeatherClient;
import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherClient weatherClient;

    @GetMapping
    public ResponseEntity<String> getVilageFcst(@Valid @RequestBody WeatherRequestDto weatherRequestDto
                                                , Errors errors){
        if(errors.hasErrors()){
            throw new CustomException(ErrorCode.NotnullWeatherCoordinate);
        }


        return ResponseEntity.ok(weatherClient.getVilageFcst(weatherRequestDto));
    }
}
