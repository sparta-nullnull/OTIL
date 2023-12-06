package com.sparta.nullnull.user.repository;

import com.sparta.nullnull.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByAccountId(String accountId);

    Optional<User> findByEmail(String email);

    Optional<User> findByAccountIdAndPassword(String accountId, String password);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByKakaoId(Long kakaoId);
}
