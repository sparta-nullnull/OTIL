package com.spartanullnull.otil.domain.post.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class NotFoundPostException extends DomainException {

    public NotFoundPostException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.NOT_FOUND_POST),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public NotFoundPostException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.NOT_FOUND_POST),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
