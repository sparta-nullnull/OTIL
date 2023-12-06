package com.sparta.nullnull.user.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    String username;
    String password;
    String nickname;

}
