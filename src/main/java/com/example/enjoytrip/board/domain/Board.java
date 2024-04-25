package com.example.enjoytrip.board.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Board {
    private Integer boardId;
    private Integer touristspotId;
    private Integer accountId;
    private String boardTitle;
    private String boardContent;
}

