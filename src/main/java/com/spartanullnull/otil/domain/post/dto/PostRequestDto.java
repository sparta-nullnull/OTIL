package com.spartanullnull.otil.domain.post.dto;

import java.util.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private List<String> categoryList;
}
