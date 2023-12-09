package com.spartanullnull.otil.domain.recommend.repository;

import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.recommend.entity.Recommend;
import com.spartanullnull.otil.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    // 특정 게시물에 대한 추천 개수 조회
    Long countByPostId(Long postId);

    // 특정 게시물에 대한 추천 수를 내림차순으로 정렬하여 조회
    @Query("SELECT r.post.id, COUNT(r) AS recommendCount FROM Recommend r GROUP BY r.post.id ORDER BY recommendCount DESC")
    List<Object[]> findTop3PostsByRecommends(Pageable pageable);
}
