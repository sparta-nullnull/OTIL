package com.spartanullnull.otil.domain.reportpost.repository;

import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import com.spartanullnull.otil.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost,Long> {

    Page<ReportPost> findByUser(User user, Pageable pageable);

    ReportPost findByUserAndId(User user, Long postid);
}
