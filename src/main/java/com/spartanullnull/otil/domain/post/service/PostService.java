package com.spartanullnull.otil.domain.post.service;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.category.service.*;
import com.spartanullnull.otil.domain.comment.entity.*;
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
@Transactional
public class PostService {

    private final CategoryService categoryService;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = findById(postId);
        List<PostCommentResponseDto> commentResponseDtoList = commentList(post);
        return PostResponseDto.of(post,commentResponseDtoList);
    }

    public List<PostCategoryResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream()
            .map(PostCategoryResponseDto::of)
            .collect(Collectors.toList());
    }

    public PostCategoryResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.builder()
            .user(user)
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .build());

        List<Category> categories =
            categoryService.buildAndSaveCategoriesByRequest(requestDto.getCategoryList(), post);

        return PostCategoryResponseDto.of(post, categories);
    }

    public PostCategoryResponseDto modifyPost(Long postId, User user, PostRequestDto requestDto) {
        Post post = findById(postId);
        checkAuthority(post, user.getAccountId());

        post.modifyPost(requestDto.getTitle(), requestDto.getContent(),
            categoryService.buildAndSaveCategoriesByRequest(requestDto.getCategoryList(), post));

        return PostCategoryResponseDto.of(post);
    }


    public void deletePost(Long postId, User user) {
        Post post = findById(postId);
        checkAuthority(post, user.getAccountId());
        postRepository.delete(post);
    }

    /**
     * postId를 통해 post를 찾고, 없다면 예외처리
     *
     * @param postId post의 PK값
     * @return post엔티티 혹은 예외 처리
     * @author 김한신
     */
    public Post findById(Long postId) {//유저를 못가져옴
        return postRepository.findById(postId)
            .orElseThrow(
                () -> new NotFoundPostException("postId", postId.toString(), "해당 TIL이 존재하지 않아요!"));
    }

    /**
     * 로그인 사용자와 리소스 소유주 사용자 간 일치 검증
     *
     * @param post      게시글
     * @param accountId 로그인 사용자 accountId
     * @author 김한신
     */
    private void checkAuthority(Post post, String accountId) {
        if (!post.getUser().getAccountId().equals(accountId)) {
            throw new NotAuthorOfPostException("accountId", accountId, "작성자만 할 수 있는 기능이에요!");
        }
    }
    private List<PostCommentResponseDto> commentList(Post post) {
        List<PostCommentResponseDto> postCommentResponseDtoList = new ArrayList<>();
        List<Comment> commentList = post.getComments();

        for (Comment comment : commentList) {
            postCommentResponseDtoList.add(PostCommentResponseDto.of(comment));
        }
        return postCommentResponseDtoList;
    }
}
