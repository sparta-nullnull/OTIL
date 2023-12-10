package com.spartanullnull.otil.domain.like.controller;

import com.spartanullnull.otil.domain.like.service.*;
import com.spartanullnull.otil.security.Impl.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 댓글 좋아요 누르기 API
    @PostMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> createLike(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 로그인한 사용자 체크
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "로그인한 사용자만 댓글 좋아요를 누를 수 있습니다.");
        }

        likeService.createLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 성공!");
    }

    // 댓글 좋아요 취소하기 API
    @DeleteMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> deleteLike(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 로그인한 사용자 체크
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "로그인한 사용자만 댓글 좋아요를 취소할 수 있습니다.");
        }

        likeService.deleteLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 취소!");
    }

    // 댓글 좋아요 개수 조회 API
    @GetMapping("/{postId}/comments/{commentId}/count")
    public ResponseEntity<String> getLikeCount(@PathVariable Long commentId) {
        Long likeCount = likeService.getLikeCount(commentId);
        return ResponseEntity.ok("댓글 좋아요 : " + likeCount);
    }
}