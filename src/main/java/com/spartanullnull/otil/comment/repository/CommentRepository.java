package com.spartanullnull.otil.comment.repository;

import com.spartanullnull.otil.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
