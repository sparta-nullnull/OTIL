package com.spartanullnull.otil.domain.category.repository;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.post.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findByCategoryNameAndPost(String categoryName, Post post);

    List<Category> saveAll(List<Category> categories);
}
