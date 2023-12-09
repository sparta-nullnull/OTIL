package com.spartanullnull.otil.domain.recommend.repository;

import com.spartanullnull.otil.domain.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

}
