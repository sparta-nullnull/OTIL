package com.spartanullnull.otil.domain.reportpost.service;

import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import com.spartanullnull.otil.domain.reportpost.repository.ReportPostRepository;
import com.spartanullnull.otil.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;
    private final PasswordEncoder passwordEncoder;

    public ReportPostResponseDto createReport(ReportPostRequestDto requestDto, User user) {
        if (passwordCheck(user, requestDto.getPassword())) {
            ReportPost reportPost = new ReportPost(requestDto);
            reportPost.setUser(user);

            reportPostRepository.save(reportPost);
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("게시글 생성 오류 발생");
        }
    }

    @Transactional(readOnly = true)
    public Page<ReportPostResponseDto> getUserAllReportPost(User user, int page,
        int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportPost> reportPosts;
        reportPosts = reportPostRepository.findByUser(user, pageable);

        return reportPosts.map(ReportPostResponseDto::new);
    }

    @Transactional
    public Page<ReportPostResponseDto> getAdminAllReportPost(int page, int size,
        String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportPost> reportPosts;
        reportPosts = reportPostRepository.findAll(pageable);

        return reportPosts.map(ReportPostResponseDto::new);
    }

    public ReportPostResponseDto getReport(Long postid, String password, User user) {
        if (passwordCheck(user, password)) {
            ReportPost reportPost = getReportPost(postid);
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("게시글 단건 조회 오류 발생");
        }
    }

    @Transactional
    public ReportPostResponseDto updateReport(Long postid, ReportPostRequestDto requestDto,
        User user) {
        String password = requestDto.getPassword();
        if (passwordCheck(user, password)) {
            ReportPost reportPost = getPost(postid);
            reportPost.setUser(user);
            reportPost.setName(requestDto.getName());
            reportPost.setTitle(requestDto.getTitle());
            reportPost.setContent(requestDto.getContent());
            reportPostRepository.save(reportPost);
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("비밀번호 오류 발생");
        }
    }

    private ReportPost getPost(Long postid) {
        return reportPostRepository.findById(postid).orElseThrow(
            () -> new IllegalArgumentException("해당 신고 게시글이 존재하지 않습니다")
        );
    }

    @Transactional
    public void deleteReport(Long postid, String password, User user) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            ReportPost reportPost = getReportPost(postid);
            if (reportPost != null) {
                reportPostRepository.deleteById(postid);
            }
        } else {
            throw new IllegalArgumentException("삭제 오류 발생: 비밀번호가 일치하지 않습니다.");
        }
    }

    private ReportPost getReportPost(Long postid) {
        return reportPostRepository.findById(postid).orElseThrow(
            () -> new IllegalArgumentException("해당 신고 게시글이 존재하지 않습니다.")
        );
    }

    private boolean passwordCheck(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword()) && password != null;
    }


}
