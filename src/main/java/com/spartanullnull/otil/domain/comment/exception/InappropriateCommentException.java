package com.spartanullnull.otil.domain.comment.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class InappropriateCommentException extends DomainException {

    public InappropriateCommentException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.INAPPROPRIATE_COMMENT),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public InappropriateCommentException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.INAPPROPRIATE_COMMENT),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
