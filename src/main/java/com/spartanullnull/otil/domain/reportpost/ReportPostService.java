package com.spartanullnull.otil.domain.reportpost;

import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.domain.user.entity.User;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;

    public ReportPostResponseDto createReport(ReportPostRequestDto requestDto, User user) {
        ReportPost reportPost = new ReportPost(requestDto);
        reportPost.setUser(user);

        reportPostRepository.save(reportPost);

        return new ReportPostResponseDto(reportPost);
    }

    @Transactional(readOnly = true)
    public Page<ReportPostResponseDto> getAllNewsFeeds(String password, User user, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (user.getPassword().matches(password)) {
            Page<ReportPost> reportPosts = reportPostRepository.findAll(pageable);
            return reportPosts.map(ReportPostResponseDto::new);
        }else{
            throw new RejectedExecutionException("게시글 조회 오류 발생");
        }
    }

    public ReportPostResponseDto getReport(Long postid, String password, User user) {
        if(password.equals(user.getPassword())){
            ReportPost reportPost = reportPostRepository.findById(postid).orElseThrow(
                () ->new IllegalArgumentException("해당 신고가 존재하지 않습니다.")
            );
            return new ReportPostResponseDto(reportPost);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }

    @Transactional
    public ReportPostResponseDto putReport(Long postid, String password, User user) {
        if (password.equals(user.getPassword())) {
            ReportPost reportPost = reportPostRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("해당 신고가 존재하지 않습니다.")
            );
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void deleteReport(Long postid, String password, User user) {
        if (password.equals(user.getPassword())) {
            reportPostRepository.deleteById(postid);
        } else {
            throw new IllegalArgumentException("삭제 오류 발생: 비밀번호가 일치하지 않습니다.");
        }
    }
}
