package com.spartanullnull.otil.domain.user.dto;

import jakarta.validation.constraints.*;

public record UserProfileModifyRequestDto(
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    String accountId,
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|]{8,15}$")
    String password,
    String nickname,
    @Email
    String email,
    boolean admin,
    String adminToken
) {

}
