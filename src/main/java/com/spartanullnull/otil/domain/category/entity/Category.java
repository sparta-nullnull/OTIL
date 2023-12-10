package com.spartanullnull.otil.domain.category.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.domain.post.entity.*;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Table(name = "category")
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @JsonIgnore
    @OneToMany(targetEntity = PostCategory.class, mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private final List<PostCategory> postCategories = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category category)) {
            return false;
        }
        return getId().equals(category.getId()) && getCategoryName().equals(
            category.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoryName());
    }

    public void updatePost(Post post) {
        PostCategory postCategory = PostCategory.of(post, this);
        this.postCategories.add(postCategory);
    }
}
