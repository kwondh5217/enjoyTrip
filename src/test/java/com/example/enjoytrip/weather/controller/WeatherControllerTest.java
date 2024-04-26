package com.example.enjoytrip.weather.controller;

import com.example.enjoytrip.config.SecurityConfig;
import com.example.enjoytrip.weather.WeatherController;
import com.example.enjoytrip.weather.client.WeatherClient;
import com.example.enjoytrip.weather.dto.WeatherRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@ActiveProfiles("test")
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WeatherClient weatherClient;

    @DisplayName("위치 정보가 없는 날씨조회는 실패하는 테스트")
    @Test
    void getVilageFcst_fail() throws Exception{
        // given
        int numOfRows = 50;
        int pageNo = 1;
        String dataType = "json";
        String baseDate = "20240425";
        String baseTime = "0500";

        WeatherRequestDto requestDto = WeatherRequestDto.builder()
                .numOfRows(numOfRows)
                .pageNo(pageNo)
                .dataType(dataType)
                .baseDate(baseDate)
                .baseTime(baseTime)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(get("/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorMessage").exists());
    }

}