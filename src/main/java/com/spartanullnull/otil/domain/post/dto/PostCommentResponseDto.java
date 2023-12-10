package com.spartanullnull.otil.domain.post.dto;

import com.spartanullnull.otil.domain.comment.entity.*;
import lombok.*;

@Getter
@Builder
public class PostCommentResponseDto {
    private  String commentText;
    private String accountId;

    public static PostCommentResponseDto of(Comment comment) {

        return PostCommentResponseDto.builder()
            .commentText(comment.getCommentText())
            .accountId(comment.getUser().getAccountId())
            .build();

    }
}
