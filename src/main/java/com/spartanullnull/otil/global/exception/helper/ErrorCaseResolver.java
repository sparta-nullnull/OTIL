package com.spartanullnull.otil.global.exception.helper;

import com.spartanullnull.otil.global.exception.entity.*;
import jakarta.annotation.*;
import java.util.*;
import lombok.*;
import org.springframework.context.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Component
@Getter
@RequiredArgsConstructor
public final class ErrorCaseResolver {

    private final static String DEFAULT_MESSAGE = "messageKeyNotFound";
    static MessageSource messageSource;
    private final MessageSource wiredMessageSource;

    // 에러 케이스 코드를 반환
    public static int getCode(ErrorCase errorCase) throws NoSuchMessageException {
        return Integer.parseInt(getMessage(errorCase.getCode()));
    }

    // 에러 메세지를 반환
    public static String getMsg(ErrorCase errorCase) throws NoSuchMessageException {
        return getMessage(errorCase.getMsg());
    }

    // 에러에 대한 HTTP STATUS를 반환
    public static HttpStatus getStatus(ErrorCase errorCase) throws NoSuchMessageException {
        return HttpStatus.resolve(
            Integer.parseInt(
                getMessage(errorCase.getStatus())
            )
        );
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    static String getMessage(String code, Object[] args) throws NoSuchMessageException {
        String message = messageSource.getMessage(code, args, DEFAULT_MESSAGE, Locale.getDefault());
        assert message != null;
        if (message.equals(DEFAULT_MESSAGE)) {
            throw new NoSuchMessageException(
                "missing translation for messageKey : " + code + " of locale : "
                    + Locale.getDefault().getLanguage());
        }
        return message;
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private static String getMessage(String code) {
        return getMessage(code, null);
    }

    @PostConstruct
    public void init() {
        messageSource = wiredMessageSource;
    }
}
