package com.sparta.nullnull.user.login;

import com.sparta.nullnull.CommonResponseDto;
import com.sparta.nullnull.jwt.JwtUtil;
import com.sparta.nullnull.user.entity.User;
import com.sparta.nullnull.user.userenum.UserRoleEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<CommonResponseDto> Login(@RequestBody LoginRequestDto requestDto) {
        try {
            loginService.login(requestDto.getAccountId(), requestDto.getPassword());
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공",HttpStatus.OK.value()));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
