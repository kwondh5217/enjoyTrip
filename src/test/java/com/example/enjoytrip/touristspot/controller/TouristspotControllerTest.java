package com.example.enjoytrip.touristspot.controller;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import com.example.enjoytrip.touristspot.service.TouristspotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = TouristspotController.class)
class TouristspotControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TouristspotService touristspotService;

    @DisplayName("여행지 정보를 10개만 조회한다")
    @Test
    void findAll_page() throws Exception {
        // given
        List<TouristSpot> touristSpotList = new ArrayList<>();
        IntStream.range(1, 11).forEach(i -> {
            touristSpotList.add(TouristSpot.builder()
                    .touristspotId(i)
                    .touristspotTitle("test")
                    .touristspotAddr("test")
                    .touristspotZipcode("0000")
                    .touristspotSidoCode(12)
                    .touristspotGugunCode(23)
                    .touristspotLongitude(BigDecimal.valueOf(123))
                    .touristspotLatitude(BigDecimal.valueOf(456))
                    .build());
        });
        given(touristspotService.findAll(any(PageDto.class))).willReturn(touristSpotList);

        // when
        ResultActions resultActions = mockMvc.perform(get("/touristspot")
                .param("pageNum", "0")
                .param("pageSize", "10"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());
    }




}