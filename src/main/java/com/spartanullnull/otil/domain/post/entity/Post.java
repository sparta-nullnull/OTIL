package com.spartanullnull.otil.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spartanullnull.otil.category.entity.Category;
import com.spartanullnull.otil.comment.entity.Comment;
import com.spartanullnull.otil.recommend.entity.Recommend;
import com.spartanullnull.otil.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Category.class, mappedBy = "post", cascade = CascadeType.PERSIST,orphanRemoval = true)
	private final List<Category> categories = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Recommend.class, mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
	private final List<Recommend> recommends = new ArrayList<>();
}