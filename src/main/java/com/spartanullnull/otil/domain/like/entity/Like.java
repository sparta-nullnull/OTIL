package com.spartanullnull.otil.domain.like.entity;

import com.fasterxml.jackson.annotation.*;
import com.spartanullnull.otil.domain.comment.entity.*;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
