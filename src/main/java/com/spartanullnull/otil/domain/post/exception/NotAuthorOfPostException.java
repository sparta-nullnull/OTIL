package com.spartanullnull.otil.domain.post.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class NotAuthorOfPostException extends DomainException {

    public NotAuthorOfPostException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_POST),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public NotAuthorOfPostException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.NOT_AUTHOR_OF_POST),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
