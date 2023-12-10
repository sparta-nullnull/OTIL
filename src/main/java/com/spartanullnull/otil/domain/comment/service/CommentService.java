package com.spartanullnull.otil.domain.comment.service;

import com.spartanullnull.otil.domain.comment.dto.CommentRequestDto;
import com.spartanullnull.otil.domain.comment.dto.CommentResponseDto;
import com.spartanullnull.otil.domain.comment.entity.Comment;
import com.spartanullnull.otil.domain.comment.exception.NotAuthorOfCommentException;
import com.spartanullnull.otil.domain.comment.exception.NotFoundCommentException;
import com.spartanullnull.otil.domain.comment.repository.CommentRepository;
import com.spartanullnull.otil.domain.like.service.LikeService;
import com.spartanullnull.otil.domain.post.entity.Post;
import com.spartanullnull.otil.domain.post.exception.NotFoundPostException;
import com.spartanullnull.otil.domain.post.repository.PostRepository;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.exception.UserNotFoundException;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeService likeService;

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

        return CommentResponseDto.fromEntity(savedComment, likeService.getLikeCount(comment.getId()));
    }

    // 특정 게시글에 대한 댓글 목록 조회 기능
    // PostId       조회할 댓글이 속한 TIL의 Id
    // return       댓글 목록의 응답 Dto 리스트
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        // 특정 게시물에 대한 댓글 목록 조회
        List<Comment> comments = commentRepository.findByPostId(postId);

        // 댓글 목록을 Dto로 변환하여 반환
        return comments.stream()
            .map(comment -> {
                Long likeCount = likeService.getLikeCount(comment.getId());
                return CommentResponseDto.fromEntity(comment, likeCount);
            })
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

        return CommentResponseDto.fromEntity(updateComment, likeService.getLikeCount(commentId));
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