package com.spartanullnull.otil.domain.category.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.domain.user.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

}
