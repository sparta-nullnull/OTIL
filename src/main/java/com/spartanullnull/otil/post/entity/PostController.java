package com.spartanullnull.otil.post.entity;

import com.spartanullnull.otil.post.dto.*;
import com.spartanullnull.otil.post.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
//    public ResponseEntity<?> addPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.createPost(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

}
