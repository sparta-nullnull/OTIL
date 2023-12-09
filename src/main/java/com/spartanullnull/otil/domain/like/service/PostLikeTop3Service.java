package com.spartanullnull.otil.domain.like.service;

import com.spartanullnull.otil.domain.like.repository.LikeRepository;
import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeTop3Service {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    /**
     *  좋아요가 가장 많은 상위 3개의 게시물을 조회하는 메서드
     *
     * @return 상위 3개의 게시물 목록
     */

    public List<Post> getTop3PostsByLikes() {
        // 좋아요가 가장 많은 상위 3개의 게시물의 데이터를 조회
        List<Object[]> top3PostsData = likeRepository.findTop3PostsByLikes(PageRequest.of(0, 3));
        List<Post> top3Posts = new ArrayList<>();

        // 상위 3개의 게시물 ID를 추출하여 조회
        for (Object[] data : top3PostsData) {
            Long postId = (Long) data[0];
            Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다"));
            top3Posts.add(post);
        }
        return top3Posts;
    }
}