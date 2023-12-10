package com.spartanullnull.otil.global.aspect;

import com.spartanullnull.otil.domain.comment.entity.*;
import com.spartanullnull.otil.domain.comment.exception.*;
import com.spartanullnull.otil.domain.comment.repository.*;
import com.spartanullnull.otil.domain.post.repository.*;
import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.domain.user.repository.*;
import com.spartanullnull.otil.security.Impl.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.aspectj.lang.annotation.*;

/**
 * 모든 도메인에 대해 리소스 요청 시, 로그인 사용자와 소유주 사용자 일치 검증하는 역할의 Aspect
 *
 * @author 임지훈
 * @deprecated 도메인 리소스 검증은 비즈니스와 밀접하다고 판단함. 이러한 이유로 본 클래스는 deprecated 되었음
 */
@Deprecated
@Slf4j(topic = "Check Resource's Author")
@Aspect
@RequiredArgsConstructor
public class AuthorValidationAspect {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 프로필 수정 :: modifyUser
    @Pointcut("execution(* com.spartanullnull.otil.domain.user.controller.UserProfileController.modifyUserProfile())")
    private void validateUserPointCut() {
    }

    // 게시글 수정 :: modifyPost
    // 게시글 삭제 :: deletePost
    // 아직 merge가 안 되어서 메서드명을 추측해봄
//    @Pointcut(
//        "execution(* com.spartanullnull.otil.domain.post.controller.PostController.modifyPost()) || "
//            + "execution(* com.spartanullnull.otil.domain.post.controller.PostController.deletePost())")
//    private void validatePostPointCut() {
//    }

    // 댓글 수정
    // 댓글 삭제
    @Pointcut(
        "execution(* com.spartanullnull.otil.domain.comment.controller.CommentController.updateComment()) || "
            + "execution(* com.spartanullnull.otil.domain.comment.controller.CommentController.deleteComment())")
    private void validateCommentPointCut() {
    }

    @Before(value = "validateCommentPointCut() && args(postId,commentId,userDetails,..)", argNames = "postId,commentId,userDetails")
    public void validateOwnerOfComment(Long postId, Long commentId, UserDetailsImpl userDetails)
        throws Throwable {
        try {
            validateOwnerOfCommentMethod(postId, commentId, userDetails);
        } catch (Throwable throwable) {
            log.error("Message of Thrown : " + throwable.getMessage());
            log.error("Cause of Thrown : " + throwable.getCause());
            throw throwable;
        }
    }

    /**
     * 로그인한 사용자가 POST와 COMMENT의 주인인지 검증하는 역할의 메서드
     *
     * @param postId      post PK 아이디 값
     * @param commentId   comment PK 아이디 값
     * @param userDetails 로그인한 사용자
     */
    private void validateOwnerOfCommentMethod(Long postId, Long commentId,
        UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Comment> foundComments = commentRepository.findByIdAndPostId(commentId, postId);
        boolean isNotOwnerOfComment = foundComments.stream()
            .filter(comment ->
                comment.getUser().equals(user)
            )
            .findAny()
            .isEmpty();
        if (isNotOwnerOfComment) {
            throw new NotAuthorOfCommentException("commentId", commentId.toString(),
                "해당 댓글에 대한 소유주가 아닙니다.");
        }
    }
}
