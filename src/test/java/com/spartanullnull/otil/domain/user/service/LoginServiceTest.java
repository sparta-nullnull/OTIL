package com.spartanullnull.otil.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.entity.UserRoleEnum;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import com.spartanullnull.otil.security.config.WebSecurityConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {WebSecurityConfig.class})
public class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    String password = passwordEncoder.encode("asdf1234!@#");
    User user = new User("asdf1234",password,"tony121","10eg@gmail.com", UserRoleEnum.USER);

    @BeforeEach
    void setUp() {
        if(userRepository.findByAccountId(user.getAccountId()).isEmpty()){
            userRepository.save(user);
        }
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void 패스워드인코더_주입_검증(){
        assertEquals(passwordEncoder.getClass(), BCryptPasswordEncoder.class);
    }

//    @Test
//    public void 로그인시도_해피케이스(){
//        // GIVEN
//        String accountId = user.getAccountId();
//        String userPassword = user.getPassword();
//
//        // WHEN
//        RuntimeException exception = assertThrows(RuntimeException.class,()->
//            loginService.login(accountId, userPassword)
//        );
//
//        // THEN
//        System.out.println("exception.getMessage() = " + exception.getMessage());
//    }


    @Test
    public void 비밀번호_확인처리_검증(){
        // GIVEN
        String localAccountId = "asdf1234";
        String localPassword = "asdf1234!@#";


        // WHEN
        User foundUser = userRepository.findByAccountIdAndPassword(localAccountId,localPassword).orElseThrow(
            () -> new UsernameNotFoundException("회원정보 불일치")
        );
        boolean isMatched = passwordEncoder.matches(localPassword, foundUser.getPassword());

        // THEN
        assertEquals(foundUser.getAccountId(),user.getAccountId());
        assertTrue(isMatched);
    }
}
