package com.spartanullnull.otil.domain.user.dto;

import com.spartanullnull.otil.domain.user.entity.*;

public record UserProfileGetResponseDto(
    String accountId,
    String password,
    String nickname,
    String email
) {

    public static UserProfileGetResponseDto of(User user) {
        return new UserProfileGetResponseDto(
            user.getAccountId(),
            user.getPassword(),
            user.getNickname(),
            user.getEmail()
        );
    }
}
