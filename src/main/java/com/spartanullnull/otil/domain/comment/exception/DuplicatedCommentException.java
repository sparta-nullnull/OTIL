package com.spartanullnull.otil.domain.comment.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class DuplicatedCommentException extends DomainException {

    public DuplicatedCommentException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.DUPLICATED_COMMENT),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public DuplicatedCommentException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.DUPLICATED_COMMENT),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
