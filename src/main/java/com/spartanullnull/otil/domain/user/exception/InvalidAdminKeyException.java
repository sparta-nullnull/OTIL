package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class InvalidAdminKeyException extends DomainException {

    public InvalidAdminKeyException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.INVALID_ADMIN_KEY),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public InvalidAdminKeyException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.INVALID_ADMIN_KEY),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
