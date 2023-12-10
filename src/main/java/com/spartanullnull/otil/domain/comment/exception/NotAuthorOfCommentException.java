package com.spartanullnull.otil.domain.comment.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class NotAuthorOfCommentException extends DomainException {

    public NotAuthorOfCommentException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_COMMENT),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public NotAuthorOfCommentException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_COMMENT),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
