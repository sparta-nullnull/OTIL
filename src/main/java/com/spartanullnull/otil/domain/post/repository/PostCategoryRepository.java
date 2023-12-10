package com.spartanullnull.otil.domain.post.repository;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.post.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {

    List<Category> findByPost(Post post);
}
