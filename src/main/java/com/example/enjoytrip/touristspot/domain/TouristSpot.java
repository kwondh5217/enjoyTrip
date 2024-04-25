package com.example.enjoytrip.touristspot.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class TouristSpot {
    private Integer touristspotId;
    private String touristspotTitle;
    private String touristspotAddr;
    private String touristspotZipcode;
    private Integer touristspotSidoCode;
    private Integer touristspotGugunCode;
    private BigDecimal touristspotLatitude;
    private BigDecimal touristspotLongitude;
}
