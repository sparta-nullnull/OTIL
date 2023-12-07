package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.*;
import java.time.*;

public class IncorrectAccountIdOrPasswordException extends DomainException {

    public IncorrectAccountIdOrPasswordException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.INCORRECT_ACCOUNT_ID_OR_PASSWORD),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public IncorrectAccountIdOrPasswordException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.INCORRECT_ACCOUNT_ID_OR_PASSWORD),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
