package com.spartanullnull.otil.domain.user.service;

import com.spartanullnull.otil.domain.user.dto.*;
import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.domain.user.exception.*;
import com.spartanullnull.otil.domain.user.helper.*;
import com.spartanullnull.otil.domain.user.repository.*;
import java.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    /**
     * 사용자 아이디를 통한 프로필 조회
     *
     * @param userId 사용자 아이디
     * @return 프로필 조회 결과
     */
    @Transactional(readOnly = true)
    public ResponseEntity<UserProfileGetResponseDto> getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("userId", userId.toString()));
        UserProfileGetResponseDto responseDto = UserProfileGetResponseDto.of(user);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 사용자 아이디와 프로필 수정요청을 통한 프로필 수정
     *
     * @param userId                      사용자 아이디
     * @param userProfileModifyRequestDto 프로필 수정요청
     * @return 프로필 수정 결과
     */
    public ResponseEntity<UserProfileModifyResponseDto> modifyUserProfile(
        Long userId,
        UserProfileModifyRequestDto userProfileModifyRequestDto
    ) {
        // 사용자 영속화
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("userId", userId.toString()));

        // 수정요청에 대해 패스워드 변경값 확인
        Optional<String> encodedPasswordOrNull = PasswordResolver.resolvePasswordOrNull(
            user.getPassword(), userProfileModifyRequestDto.password());
        String password = encodedPasswordOrNull.orElse(user.getPassword());
        // 수정요청에 대해 권한 변경값 확인
        UserRoleEnum userRole = UserRoleResolver.resolveUserRole(
            userProfileModifyRequestDto.admin(), userProfileModifyRequestDto.adminToken());

        // 수정
        user.modifyByRequest(userProfileModifyRequestDto, password, userRole);
        userRepository.save(user);

        // 결과 반환
        UserProfileModifyResponseDto responseDto = UserProfileModifyResponseDto.of(user);
        return ResponseEntity.ok(responseDto);
    }
}
