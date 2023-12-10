package com.spartanullnull.otil.domain.post.dto;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.global.dto.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto extends CommonResponseDto {

    private Long id;
    private String accountId;
    private String title;
    private String content;
    private List<String> categoryList;
    private LocalDateTime createdAt;
    private List<PostCommentResponseDto> commentList;

    public static PostResponseDto of(Post post, List<PostCommentResponseDto> responseDtoList) {
        return PostResponseDto.builder()
            .id(post.getId())
            .accountId(post.getUser().getAccountId())
            .title(post.getTitle())
            .content(post.getContent())
            .categoryList(
                post.getPostCategories()
                    .stream()
                    .filter(postCategory -> postCategory.getPost().equals(post))
                    .map(postCategory -> postCategory.getCategory().getCategoryName())
                    .toList()
            )
            .commentList(responseDtoList)
            .createdAt(post.getCreatedAt())
            .build();
    }
}
