package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class ExistingUserException extends DomainException {

    public ExistingUserException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.EXISTING_USER),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public ExistingUserException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.EXISTING_USER),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
