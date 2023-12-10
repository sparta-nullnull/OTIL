package com.spartanullnull.otil.global.exception.handler;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.nio.file.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.context.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 형식에 어긋난 입력형식오류예외 발생 시 예외처리 핸들러
     *
     * @param ex 입력형식오류예외
     * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
     * @author 임지훈
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        ErrorCode errorCode = ErrorCode.of(ErrorCase.DATA_ERROR);
        final ErrorResponse response = ErrorResponse.of(errorCode, ex.getBindingResult());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }


    /**
     * 권한 제한 예외 발생 시 예외처리 핸들러
     *
     * @param ex 권한 제한 예외
     * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
     * @author 임지훈
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("handleDomainException", ex);
        ErrorCode errorCode = ErrorCode.of(ErrorCase.ACCESS_DENIED);
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * 메세지 국제화 처리 오휴 발생 시 예외처리 핸들러
     *
     * @param ex 메세지 국제화 처리 오휴
     * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
     * @author 임지훈
     */
    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<?> handleNoSuchMessageException(NoSuchMessageException ex) {
        log.error("handleNoSuchMessageException", ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }


    /**
     * 비즈니스 관련 예외 발생 시 예외처리 핸들러
     *
     * @param ex 비즈니스 도메인 예외
     * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
     * @author 임지훈
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        log.error("handleDomainException", ex);
        ErrorCode errorCode = ex.getErrorCode();
        ErrorDetail errorDetail = ex.getErrorDetail();
        final ErrorResponse response = ErrorResponse.of(errorCode, errorDetail);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
