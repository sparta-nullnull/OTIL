package com.spartanullnull.otil.domain.like.service;

import com.spartanullnull.otil.domain.like.entity.Like;
import com.spartanullnull.otil.domain.like.repository.LikeRepository;
import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.post.repository.PostRepository;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 사용자가 TIL에 좋아요를 누르는 메서드
     *
     * @param userId    좋아요를 누르는 사용자
     * @param postId    좋아요를 누를 게시물 ID
     */
    @Transactional
    public void createLikeForPost(Long userId , Long postId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("TIL을 찾을 수 없습니다."));
        
        if (likeRepository.existsByUserAndPost(user, post)) {
            // 좋아요가 이미 존재하면 처리하지 않음
            return;
        }

        likeRepository.save(Like.builder().user(user).post(post).build());
    }

    /**
     * 사용자가 TIL에 좋아요를 취소하는 메서드
     *
     * @param userId    좋아요 취소하는 사용자
     * @param postId    좋아요를 취소할 게시물 ID
     */
    @Transactional
    public void deleteLikeForPost(Long userId, Long postId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("TIL을 찾을 수 없습니다."));

        if (likeRepository.existsByUserAndPost(user, post)) {
            likeRepository.deleteByUserAndPost(user, post);
        }
    }

    /**
     *
     * @param postId    조회할 게시물 ID
     * @return          게시물의 좋아요 개수
     */
    public Long getLikeCountForPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    /**
     *  좋아요가 가장 많은 상위 3개의 게시물을 조회하는 메서드
     *
     * @return 상위 3개의 게시물 목록
     */

    public List<Post> getTop3PostsByLikes() {
        // 좋아요가 가장 많은 상위 3개의 게시물의 데이터를 조회
        List<Object[]> top3PostsData = likeRepository.findTop3PostsByLikes();
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