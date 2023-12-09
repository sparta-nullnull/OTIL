package com.spartanullnull.otil.domain.post.controller;

import com.spartanullnull.otil.domain.post.dto.*;
import com.spartanullnull.otil.domain.post.service.*;
import com.spartanullnull.otil.global.dto.*;
import com.spartanullnull.otil.security.Impl.*;
import java.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto requestDto,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long postId) {
        try {
            PostResponseDto responseDto = postService.getPost(postId);
            return ResponseEntity.ok().body(responseDto);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostList() {
        List<PostResponseDto> responseDtoList = postService.getPostList();
        return ResponseEntity.ok(responseDtoList);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> modifyPost(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PostRequestDto requestDto) {
        try {
            PostResponseDto responseDto = postService.modifyPost(postId, userDetails.getUser(),
                requestDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));

        }
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
    }

}
