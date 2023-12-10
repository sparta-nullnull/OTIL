package com.spartanullnull.otil.domain.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class LoginRequestDto {

    private String accountId;
    private String password;

}
