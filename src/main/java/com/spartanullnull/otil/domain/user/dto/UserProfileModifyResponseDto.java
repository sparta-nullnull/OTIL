package com.spartanullnull.otil.domain.user.dto;

import com.spartanullnull.otil.domain.user.entity.*;

public record UserProfileModifyResponseDto(
    String accountId,
    String password,
    String nickname,
    String email,
    boolean admin
) {

    public static UserProfileModifyResponseDto of(User user) {
        return new UserProfileModifyResponseDto(
            user.getAccountId(),
            user.getPassword(),
            user.getNickname(),
            user.getEmail(),
            UserRoleEnum.ADMIN.equals(user.getRole())
        );
    }
}
