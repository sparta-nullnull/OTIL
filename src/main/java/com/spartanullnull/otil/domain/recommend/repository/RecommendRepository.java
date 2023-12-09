package com.spartanullnull.otil.domain.recommend.repository;

import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.recommend.entity.Recommend;
import com.spartanullnull.otil.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    // 특정 게시물에 대한 추천 개수 조회
    Long countByPostId(Long postId);
}
