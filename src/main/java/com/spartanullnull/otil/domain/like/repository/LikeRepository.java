package com.spartanullnull.otil.domain.like.repository;

import com.spartanullnull.otil.domain.comment.entity.Comment;
import com.spartanullnull.otil.domain.like.entity.Like;
import com.spartanullnull.otil.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUser(User user, Comment comment);

    void deleteByUser(User user, Comment comment);

    // 특정 댓글에 대한 좋아요 개수 조회
    Long countByCommentId(Long commentId);
}