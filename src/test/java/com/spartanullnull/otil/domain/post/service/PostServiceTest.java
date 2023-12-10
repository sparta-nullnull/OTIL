package com.spartanullnull.otil.domain.post.service;

import static org.junit.jupiter.api.Assertions.*;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.category.repository.*;
import com.spartanullnull.otil.domain.category.service.*;
import com.spartanullnull.otil.domain.post.dto.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.domain.post.repository.*;
import com.spartanullnull.otil.domain.user.entity.*;
import com.spartanullnull.otil.domain.user.repository.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PostService.class, User.class, Category.class, Post.class, CategoryService.class})
class PostServiceTest {

    User user = new User("accountId", "asdg124!@%", "jihoon", "sdf123@gmail.com",
        UserRoleEnum.USER);
    PostRequestDto requestDto = new PostRequestDto("title", "content", List.of("java", "python"));


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성")
    public void 게시글_생성() {
        // GIVEN
        PostResponseDto postResponseDto = postService.createPost(requestDto, user);

        // THEN
        String title = postResponseDto.getTitle();
        String content = postResponseDto.getContent();
        List<String> categoryList = postResponseDto.getCategoryList();
        assertEquals(title, "title");
        assertEquals(content, "content");
        assertTrue(categoryList.contains("java"));
        assertTrue(categoryList.contains("python"));

        List<Post> posts = postRepository.findAll();
        System.out.println(posts.size());
        posts.forEach(System.out::println);
    }

    @Test
    @DisplayName("게시글 수정")
    public void 게시글_수정() {
        // GIVEN
        postService.createPost(requestDto, user);
        Long createdPostId = postRepository.findAll().get(0).getId();
        PostRequestDto requestDto = new PostRequestDto("titleModified", "contentModified",
            List.of("C#", "python"));
        // WHEN
        PostResponseDto postResponseDto = postService.modifyPost(createdPostId, user,
            requestDto);

        // THEN
        assertEquals(postResponseDto.getTitle(), "titleModified");
        assertEquals(postResponseDto.getContent(), "contentModified");
        assertFalse(postResponseDto.getCategoryList().contains("java"));
        assertTrue(postResponseDto.getCategoryList().contains("C#"));
        assertTrue(postResponseDto.getCategoryList().contains("python"));
    }
}
