package com.spartanullnull.otil.domain.like.controller;

import com.spartanullnull.otil.domain.like.service.PostLikeTop3Service;
import com.spartanullnull.otil.domain.post.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostLikeTop3Controller {

    private final PostLikeTop3Service likeService;

    // 좋아요가 가장 많은 상위 3개의 게시물 조회 API
    @GetMapping("/top3")
    public ResponseEntity<List<Post>> getTop3PostsByLikes() {
        // 좋아요가 가장 많은 상위 3개의 게시물 조회
        List<Post> top3Posts = likeService.getTop3PostsByLikes();
        return ResponseEntity.ok(top3Posts);
    }
}