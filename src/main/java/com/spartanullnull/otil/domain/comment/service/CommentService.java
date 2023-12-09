package com.spartanullnull.otil.domain.comment.service;

import com.spartanullnull.otil.domain.comment.dto.*;
import com.spartanullnull.otil.domain.comment.entity.*;
import com.spartanullnull.otil.domain.comment.exception.NotAuthorOfCommentException;
import com.spartanullnull.otil.domain.comment.exception.NotFoundCommentException;
import com.spartanullnull.otil.domain.comment.repository.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.domain.post.exception.*;
import com.spartanullnull.otil.domain.post.repository.*;
import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.domain.user.exception.*;
import com.spartanullnull.otil.domain.user.repository.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 작성 기능
    // postId       작성된 댓글이 속한 TIL의 Id
    // userId       댓글을 작성한 사용자의 Id
    // requestDto   댓글 작성 요청 Dto
    // return       작성된 댓글의 응답 Dto
    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId,
        CommentRequestDto requestDto) {
        // 댓글 작성자 및 게시물 확인
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("userId", userId.toString(),
                "해당 Id에 대한 사용자가 없습니다."));
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundPostException("postId", postId.toString()));

        Comment comment = Comment.builder()
            .commentText(requestDto.getCommentText())
            .user(user)
            .post(post)
            .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.fromEntity(savedComment);
    }

    // 특정 게시글에 대한 댓글 목록 조회 기능
    // PostId       조회할 댓글이 속한 TIL의 Id
    // return       댓글 목록의 응답 Dto 리스트
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        // 특정 게시물에 대한 댓글 목록 조회
        List<Comment> comments = commentRepository.findByPostId(postId);

        // 댓글 목록을 Dto로 변환하여 반환
        return comments.stream()
            .map(CommentResponseDto::fromEntity)
            .collect(Collectors.toList());
    }

    /**
     * 댓글 수정 기능
     *
     * @param userId        요청을 보낸 사용자의 ID (로그인된 사용자)
     * @param commentId     수정할 댓글의 ID
     * @param requestDto    수정할 댓글의 내용이 담긴 Dto
     * @return              수정된 댓글의 응답 Dto
     */
    @Transactional
    public CommentResponseDto updateComment(Long userId ,Long commentId, CommentRequestDto requestDto) {
        // 기존 댓글 확인
        Comment existingComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundCommentException("commentId", commentId.toString(),"댓글을 찾을 수 없습니다."));

        // 댓글 소유자 확인
        if (!existingComment.getUser().getId().equals(userId)) {
            throw new NotAuthorOfCommentException("userId", userId.toString() ,"댓글을 수정할 권한이 없습니다.");
        }

        Comment updateComment = Comment.builder()
            .id(existingComment.getId())
            .commentText(requestDto.getCommentText())
            .user(existingComment.getUser())
            .post(existingComment.getPost())
            .build();
        commentRepository.save(updateComment);

        return CommentResponseDto.fromEntity(updateComment);
    }

    /**
     * 댓글 삭제 기능
     *
     * @param commentId     삭제할 댓글의 ID
     * @param userId        요청을 보낸 사용자의 ID (로그인된 사용자)
     */
    public void deleteComment(Long commentId, Long userId) {
        // 댓글 확인
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundCommentException("commentId", commentId.toString(),"댓글을 찾을 수 없습니다."));

        // 댓글 소유자 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new NotAuthorOfCommentException("userId", userId.toString() ,"댓글을 수정할 권한이 없습니다.");
        }

        // 댓글 삭제
        commentRepository.deleteById(commentId);
    }
}