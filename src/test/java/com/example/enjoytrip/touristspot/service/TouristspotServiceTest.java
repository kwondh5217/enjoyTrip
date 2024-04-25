package com.example.enjoytrip.touristspot.service;

import com.example.enjoytrip.common.dto.PageDto;
import com.example.enjoytrip.touristspot.dao.TouristspotDao;
import com.example.enjoytrip.touristspot.domain.TouristSpot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TouristspotServiceTest {

    @Mock
    TouristspotDao touristspotDao;


    @DisplayName("여행지 정보를 10개만 조회한다")
    @Test
    void findAll_page(){
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
        PageDto pageDto = new PageDto(0, 10);
        given(touristspotDao.findAll(any(PageDto.class))).willReturn(touristSpotList);
        TouristspotService touristspotService = new TouristspotServiceImpl(touristspotDao);

        // when
        List<TouristSpot> all = touristspotService.findAll(pageDto);

        // then
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(10);
    }

}