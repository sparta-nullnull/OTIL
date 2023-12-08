package com.spartanullnull.otil.domain.reportpost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportPostRequestDto {

    private Long id;
    private String username;
    private String title;
    private String content;

}
