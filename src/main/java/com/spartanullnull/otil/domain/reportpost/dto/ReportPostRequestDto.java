package com.spartanullnull.otil.domain.reportpost.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportPostRequestDto {

    private Long id;
    private String name;

    @NotBlank(message = "회원 비밀번호 작성해주세요.")
    private String password;

    private String title;
    private String content;

}
