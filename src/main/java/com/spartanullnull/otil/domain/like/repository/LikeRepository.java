package com.spartanullnull.otil.domain.like.repository;

import com.spartanullnull.otil.domain.like.entity.Like;
import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    // 특정 게시물에 대한 좋아요 개수 조회
    Long countByPostId(Long postId);

    // 모든 게시물에 대한 좋아요 개수를 내림차순으로 정렬하여 조회
    @Query("SELECT p.id, COUNT(l) AS likeCount FROM Post p LEFT JOIN Like l ON p.id = l.post.id GROUP BY p.id ORDER BY likeCount DESC")
    List<Object[]> findTop3PostsByLikes(Pageable pageable);
}
