package com.spartanullnull.otil.domain.category.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class NotAuthorOfCategoryException extends DomainException {

    public NotAuthorOfCategoryException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_CATEGORY),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public NotAuthorOfCategoryException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_CATEGORY),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
