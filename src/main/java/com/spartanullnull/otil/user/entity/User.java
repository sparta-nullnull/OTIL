package com.spartanullnull.otil.user.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spartanullnull.otil.comment.entity.Comment;
import com.spartanullnull.otil.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToMany(targetEntity = Post.class, mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	private final List<Post> posts = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;
}