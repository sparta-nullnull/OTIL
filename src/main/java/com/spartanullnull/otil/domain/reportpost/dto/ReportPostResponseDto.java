package com.spartanullnull.otil.domain.reportpost.dto;

import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportPostResponseDto {

    private Long id;
    private String name;
    private String title;
    private String content;

    public ReportPostResponseDto(ReportPost reportPost){
        this.id = reportPost.getId();
        this.name = reportPost.getName();
        this.title = reportPost.getTitle();
        this.content = reportPost.getContent();
    }

}
