package com.spartanullnull.otil.domain;

//import org.springframework.security.authentication.*;
//import org.springframework.security.core.*;

import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.security.Impl.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.test.context.support.*;

public class WithMockCustomUserSecurityContextFactory implements
    WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
//        UserDetails userDetails = User.builder().username(annotation.accountId())
//            .password(annotation.password())
////            .roles(annotation.role().toString())
//            .authorities(annotation.role().toString())
//            .build();
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//            userDetails, userDetails.getPassword(),
//            userDetails.getAuthorities());
//
//        context.setAuthentication(auth);

        User user = new User(annotation.accountId(), annotation.password(), annotation.email(),
            annotation.nickname(), annotation.role());

        UserDetailsImpl principal = new UserDetailsImpl(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            principal,
            principal.getPassword(),
            principal.getAuthorities());

        context.setAuthentication(token);
        return context;
    }
}
