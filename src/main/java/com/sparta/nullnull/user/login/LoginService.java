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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public String login(String accountId, String password) {
        try {
            User user = userRepository.findByAccountIdAndPassword(accountId,password).orElseThrow(
                () -> new UsernameNotFoundException("회원정보 불일치")
            );
            if(passwordEncoder.matches(password,user.getPassword())){
                UserRoleEnum role = user.getRole();
                return jwtUtil.createToken(accountId,role);
            }else{
                throw new RuntimeException("잘못된 유저 정보");
            }
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        } catch (Exception e) {
            throw new RuntimeException("로그인에 실패했습니다.", e);
        }
    }
}
