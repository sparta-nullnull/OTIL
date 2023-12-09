package com.spartanullnull.otil.domain.post.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class DuplicatedPostException extends DomainException {

    public DuplicatedPostException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.DUPLICATED_POST),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public DuplicatedPostException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.DUPLICATED_POST),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
