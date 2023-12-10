package com.spartanullnull.otil.domain.comment.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class NotFoundCommentException extends DomainException {

    public NotFoundCommentException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.NOT_FOUND_COMMENT),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public NotFoundCommentException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.NOT_FOUND_COMMENT),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
