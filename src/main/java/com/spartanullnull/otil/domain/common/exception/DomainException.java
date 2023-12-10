package com.spartanullnull.otil.domain.common.exception;

import com.spartanullnull.otil.global.exception.entity.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainException extends RuntimeException {

    protected ErrorCode errorCode;
    protected ErrorDetail errorDetail;
}
