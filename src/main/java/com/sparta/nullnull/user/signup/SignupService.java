package com.sparta.nullnull.user.signup;

import com.sparta.nullnull.user.entity.User;
import com.sparta.nullnull.user.repository.UserRepository;
import com.sparta.nullnull.user.userenum.UserRoleEnum;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    public void singup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 존재");
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickName = userRepository.findByNickname(nickname);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 checkNickName 존재");
        }

        String email = requestDto.email;
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 email 존재");
        }

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 상이합니다.");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, nickname ,email, roleEnum);
        userRepository.save(user);
    }
}
