package com.spartanullnull.otil.domain.post.service;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.category.repository.*;
import com.spartanullnull.otil.domain.post.dto.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.domain.post.exception.*;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.builder()
            .user(user)
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .build());

        List<Category> categoryList = new ArrayList<>();
        requestDto.getCategoryList().forEach(category -> {
            categoryList.add(Category.builder()
                .categoryName(category)
                .post(post)
                .build());
        });
        categoryRepository.saveAll(categoryList);

        return PostResponseDto.of(post, categoryList);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = findById(postId);
        return PostResponseDto.of(post);
    }

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
        checkAuthority(post, user.getAccountId());
//        post.modifyPost(requestDto);
        return PostResponseDto.of(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = findById(postId);
        checkAuthority(post, user.getAccountId());
        postRepository.delete(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(
                () -> new NotFoundPostException("postId", postId.toString(), "해당 TIL이 존재하지 않아요!"));
    }

    private void checkAuthority(Post post, String accountId) {
        if (!post.getUser().getAccountId().equals(accountId)) {
            throw new NotAuthorOfPostException("accountId", accountId, "작성자만 할 수 있는 기능이에요!");
        }
    }
}