package com.example.enjoytrip.comment.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {
    private Integer commentId;
    private Integer boardId;
    private Integer accountId;
    private String commentContent;
}

