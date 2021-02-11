package me.jongwoo.springdata;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getPostTest() throws Exception {

        Post post = new Post();
        post.setTitle("spring jpa");

        postRepository.save(post);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/"+ post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("spring jpa"));

    }
}