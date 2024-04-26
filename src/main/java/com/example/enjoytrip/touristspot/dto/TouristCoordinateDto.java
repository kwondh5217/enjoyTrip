package com.example.enjoytrip.touristspot.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class TouristCoordinateDto {

    private BigDecimal northLatitude;
    private BigDecimal eastLongitude;
    private BigDecimal southLatitude;
    private BigDecimal westLongitude;
}
