package com.spartanullnull.otil.domain.post.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.comment.entity.*;
import com.spartanullnull.otil.domain.post.dto.*;
import com.spartanullnull.otil.domain.recommend.entity.*;
import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.util.*;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @JsonIgnore
    @OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();
    @JsonIgnore
    @OneToMany(targetEntity = PostCategory.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostCategory> postCategories = new ArrayList<>();
    @JsonIgnore
    @OneToMany(targetEntity = Recommend.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Recommend> recommends = new ArrayList<>();
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

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void modifyPost(String title, String content, List<Category> categoriesByRequest) {
        this.title = title;
        this.content = content;
        removeCategories();
        categoriesByRequest.forEach(this::addCategory);
    }

    private void removeCategories() {
        if (!postCategories.isEmpty()) {
            postCategories.forEach(postCategory ->
                postCategory.getCategory()
                    .getPostCategories()
                    .remove(postCategory)
            );
        }
        this.postCategories.clear();
    }

    private void addCategory(Category category) {
        PostCategory postCategory = PostCategory.of(this, category);
        postCategories.add(postCategory);
    }
}