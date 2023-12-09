package com.spartanullnull.otil.domain.user.entity;

import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column
    @NotNull
    private String password;

    @Column(nullable = false, unique = true)
    @NotNull
    private String nickname;

    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReportPost> reportPost = new ArrayList<>();

    private Long kakaoId;

    public User(String accountId, String password, String nickname, String email, UserRoleEnum role) {
        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public User(Long id, String encodedPassword, String nickname, String email, UserRoleEnum role) {
        this.kakaoId = id;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}