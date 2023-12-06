package com.spartanullnull.otil.post.service;

import com.spartanullnull.otil.post.dto.*;
import com.spartanullnull.otil.post.entity.*;
import com.spartanullnull.otil.post.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = Post.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .build();
        postRepository.save(post);

        return PostResponseDto.of(post);
    }
}
