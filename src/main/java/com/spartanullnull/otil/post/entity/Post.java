package com.spartanullnull.otil.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spartanullnull.otil.category.entity.Category;
import com.spartanullnull.otil.comment.entity.Comment;
import com.spartanullnull.otil.post.dto.*;
import com.spartanullnull.otil.recommend.entity.Recommend;
import com.spartanullnull.otil.user.entity.User;
import com.spartanullnull.otil.util.*;
import jakarta.persistence.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity = Category.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity = Recommend.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Recommend> recommends = new ArrayList<>();

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
//TODO
//    public setUser(User user) {
//        this.user = user;
//    }
}