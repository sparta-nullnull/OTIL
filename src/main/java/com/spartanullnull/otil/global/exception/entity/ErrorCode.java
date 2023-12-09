package com.spartanullnull.otil.global.exception.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.global.exception.helper.*;
import lombok.*;
import org.springframework.context.*;
import org.springframework.http.*;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ErrorCode {

    private String message;

    private int code;

    private HttpStatus status;

    public static ErrorCode of(ErrorCase errorCase) throws NoSuchMessageException {
        return new ErrorCode(
            ErrorCaseResolver.getMsg(errorCase),
            ErrorCaseResolver.getCode(errorCase),
            ErrorCaseResolver.getStatus(errorCase)
        );
    }
}
