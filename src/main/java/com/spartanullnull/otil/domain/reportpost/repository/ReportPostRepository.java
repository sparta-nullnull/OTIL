package com.spartanullnull.otil.domain.reportpost.repository;

import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost,Long> {
}
