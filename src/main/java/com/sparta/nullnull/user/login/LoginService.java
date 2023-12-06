package com.sparta.nullnull.user.login;

import com.sparta.nullnull.jwt.JwtUtil;
import com.sparta.nullnull.user.entity.User;
import com.sparta.nullnull.user.repository.UserRepository;
import com.sparta.nullnull.user.userenum.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDto requestDto) {
        try {
            User user = userRepository.findByUsernameAndPassword(requestDto.getUsername(),
                    requestDto.getPassword())
                .orElseThrow(
                    () -> new UsernameNotFoundException("login 오류 발생")
                );
            if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                UserRoleEnum role = user.getRole();
                return jwtUtil.createToken(requestDto.getUsername(),role);
            }else{
                throw new RuntimeException("로그인 오류 발생");
            }
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
