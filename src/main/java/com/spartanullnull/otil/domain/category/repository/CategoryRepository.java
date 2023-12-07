package com.spartanullnull.otil.domain.category.repository;

import com.spartanullnull.otil.domain.category.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
