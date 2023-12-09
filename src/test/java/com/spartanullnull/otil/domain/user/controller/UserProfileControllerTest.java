package com.spartanullnull.otil.domain.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.spartanullnull.otil.domain.*;
import com.spartanullnull.otil.domain.user.service.*;
import com.spartanullnull.otil.security.Impl.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

@WebMvcTest(controllers = UserProfileController.class)
//@ContextConfiguration(classes = {SecurityConfig.class})
//@ContextConfiguration(classes = {WebSecurityConfig.class, SecurityConfig.class})
class UserProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    UserProfileService userProfileService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(springSecurity()).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(
            new UserProfileController(userProfileService)).build();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("PUT : 프로필 수정하기 요청을 처리합니다.")
    public void PUT_프로필수정_핸들링() {
        // GIVEN
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        // WHEN
        String accountId = userDetails.getUser().getAccountId();

        // THEN
        System.out.println("accountId = " + accountId);
    }
}