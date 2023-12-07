package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class RequiresLoginException extends DomainException {

    public RequiresLoginException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.REQUIRES_LOGIN),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public RequiresLoginException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.REQUIRES_LOGIN),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
