package com.sparta.nullnull.user.login;

import com.sparta.nullnull.CommonResponseDto;
import com.sparta.nullnull.security.Impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    public ResponseEntity<CommonResponseDto> Login(@RequestBody LoginRequestDto requestDto){
        try {
            loginService.login(requestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인 실패",HttpStatus.BAD_REQUEST.value()));
        }
      }


}
