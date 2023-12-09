package com.spartanullnull.otil.global.exception;

public enum ErrorCase {
    /* Common */
    // 알수 없는 오류가 발생
    UNKNOWN("unKnown"),
    // 통신 중 오류가 발생
    COMMUNICATION_ERROR("communicationError"),
    // 형식에 맞지 않는 데이터 오류가 발생
    DATA_ERROR("dataError"),


    /* User */
    // 존재하지 않는 회원 관련 데이터 찾는 경우
    USER_NOT_FOUND("userNotFound"),
    // 계정 아이디 또는 비밀번호가 정확하지 않음
    INCORRECT_ACCOUNT_ID_OR_PASSWORD("incorrectAccountIdOrPassword"),
    // 해당 리소스에 접근하기 위한 권한이 없음
    ENTRY_POINT_EXCEPTION("entryPointException"),
    // 보유한 권한으로 접근할수 없는 리소스 요청
    ACCESS_DENIED("accessDenied"),
    // 이미 가입한 회원 관련 입력 예외
    EXISTING_USER("existingUser"),
    // 로그인 필요한 요청
    REQUIRES_LOGIN("requiresLogin"),
    // 로그아웃이 필요한 요청
    REQUIRES_LOGGED_OUT("requiresLoggedOut"),
    // 계정 아이디 형식 오류
    ACCOUNT_ID_VALIDATION_FAIL("accountIdValidationFail"),
    // 이미 다른 회원이 동일 계정아이디 사용하는 경우
    DUPLICATED_ACCOUNT_ID("duplicatedAccountId"),
    // 패스워드 형식 오류
    PASSWORD_VALIDATION_FAIL("passwordValidationFail"),
    // 닉네임 형식 오류
    NICKNAME_VALIDATION_FAIL("nickNameValidationFail"),
    // 이메일 형식 오류
    EMAIL_VALIDATION_FAIL("emailValidationFail"),
    // 어드민 권한 획득 키값 일치하지 않는 경우
    INVALID_ADMIN_KEY("invalidAdminKey"),


    /* Post */
    // 게시글이 존재하지 않는 경우
    NOT_FOUND_POST("notFoundPost"),
    // 요청에 대한 게시글이 이미 존재하는 경우
    DUPLICATED_POST("duplicatedPost"),
    // 사용자가 게시글의 소유주가 아닌 경우
    NOT_AUTHOR_OF_POST("notAuthorOfPost"),

    /* Comment */
    // 댓글이 존재하지 않는 경우
    NOT_FOUND_COMMENT("notFoundComment"),
    // 이미 동일한 댓글이 있어 중복되는 경우
    DUPLICATED_COMMENT("duplicatedComment"),
    // 사용자가 댓글의 소유자가 아닌 경우
    NOT_AUTHOR_OF_COMMENT("notAuthorOfComment"),
    // 부적절한 댓글 작성을 한 경우
    INAPPROPRIATE_COMMENT("inappropriateComment"),

    /* Category */
    // 카테고리가 존재하지 않는 경우
    NOT_FOUND_CATEGORY("notFoundCategory"),
    // 이미 동일한 카테고리가 있어 중복되는 경우
    DUPLICATED_CATEGORY("duplicatedCategory"),
    // 카테고리의 소유자가 아닌 경우
    NOT_AUTHOR_OF_CATEGORY("notAuthorOfCategory"),
    // 부적절한 카테고리 작성을 한 경우
    INAPPROPRIATE_CATEGORY("inappropriateCategory");

    private final String key;

    ErrorCase(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.key + ".code";
    }

    public String getMsg() {
        return this.key + ".msg";
    }

    public String getStatus() {
        return this.key + ".status";
    }
}
