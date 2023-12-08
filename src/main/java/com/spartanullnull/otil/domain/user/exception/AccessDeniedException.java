package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class AccessDeniedException extends DomainException {

    public AccessDeniedException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.ACCESS_DENIED),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public AccessDeniedException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.ACCESS_DENIED),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
