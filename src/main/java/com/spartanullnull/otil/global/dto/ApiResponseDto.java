package com.spartanullnull.otil.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto<T> {

    private T date;
    private String msg;
    private Integer statusCode;


}
