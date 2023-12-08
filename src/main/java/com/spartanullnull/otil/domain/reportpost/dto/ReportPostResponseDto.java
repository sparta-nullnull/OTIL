package com.spartanullnull.otil.domain.reportpost.dto;

import com.spartanullnull.otil.domain.reportpost.ReportPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportPostResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;

    public ReportPostResponseDto(ReportPost reportPost){
        this.id = reportPost.getId();
        this.username = reportPost.getUsername();
        this.title = reportPost.getTitle();
        this.content = reportPost.getContent();
    }

}
