package me.jongwoo.springdata;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getPost() throws Exception {

        Post post = new Post();
        post.setTitle("spring jpa");

        postRepository.save(post);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/"+ post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("spring jpa"));

    }

    @Test
    @Transactional
    public void getPosts() throws Exception{

        createPosts();

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/")
                .param("page", "0")
                .param("size","10")
                .param("sort","created,desc")
                .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Spring Data JPA...."))
        ;

    }

    private void createPosts() {
        int postsCount = 100;
        while(postsCount > 0){
            Post post = new Post();
            post.setTitle("spring jpa");
            postRepository.save(post);
            postsCount--;
        }

    }
}