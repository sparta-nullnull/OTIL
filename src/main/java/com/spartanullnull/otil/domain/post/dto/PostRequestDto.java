package com.spartanullnull.otil.domain.post.dto;

import com.spartanullnull.otil.domain.category.entity.*;
import java.util.*;
import lombok.*;

@Getter
public class PostRequestDto {

    private String title;
    private String content;
    private List<String> categoryList;
}
