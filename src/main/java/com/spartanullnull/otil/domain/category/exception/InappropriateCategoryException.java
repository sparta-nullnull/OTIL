package com.spartanullnull.otil.domain.category.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class InappropriateCategoryException extends DomainException {

    public InappropriateCategoryException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.INAPPROPRIATE_CATEGORY),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public InappropriateCategoryException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.INAPPROPRIATE_CATEGORY),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
