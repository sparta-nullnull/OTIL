package com.spartanullnull.otil.domain.post.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.domain.category.entity.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Builder
    public PostCategory(Post post, Category category) {
        this.post = post;
        this.category = category;
    }

    public static PostCategory of(Post post, Category category) {
        PostCategory postCategory = new PostCategory(post, category);
        postCategory.post.getPostCategories().add(postCategory);
        return postCategory;
    }
}

