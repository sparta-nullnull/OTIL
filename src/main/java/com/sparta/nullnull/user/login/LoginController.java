package com.sparta.nullnull.user.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.nullnull.CommonResponseDto;
import com.sparta.nullnull.jwt.JwtUtil;
import com.sparta.nullnull.user.kakao.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final KakaoService kakaoService;

    @PostMapping("/users/login")
    public ResponseEntity<CommonResponseDto> Login(@RequestBody LoginRequestDto requestDto) {
        try {
            loginService.login(requestDto.getAccountId(), requestDto.getPassword());
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto("로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/users/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204반환
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            //인증 정보 조회
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                //로그아웃 핸들러를 통해 인증 정보 및 사용자 세션 삭제
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        } catch (Exception e) {
            throw new RuntimeException("로그아웃 실패: " + e.getMessage());
        }
    }

    @GetMapping("/user/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response)
        throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
