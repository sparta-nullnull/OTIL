package com.spartanullnull.otil.domain.post.service;

import com.spartanullnull.otil.domain.post.dto.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.domain.post.repository.*;
import com.spartanullnull.otil.domain.user.entity.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = Post.builder()
            .user(user)
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .build();
        postRepository.save(post);

        return PostResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = findById(postId);
        return PostResponseDto.of(post);
    }

    //TODO
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream()
            .map(post -> PostResponseDto.of(post))
            .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto modifyPost(Long postId, User user,
        PostRequestDto requestDto) {
        Post post = findById(postId);
        findByUsername(post, user.getAccountId());
        post.modifyPost(requestDto);
        return PostResponseDto.of(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = findById(postId);
        findByUsername(post, user.getAccountId());
        postRepository.delete(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new NullPointerException("해당 TIL이 존재하지 않아요!"));
    }

    private void findByUsername(Post post, String accountId) {
        if (!post.getUser().getAccountId().equals(accountId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있어요!");
        }
    }
}
