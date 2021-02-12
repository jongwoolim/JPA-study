package me.jongwoo.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable("id") Post post){
        //도메인 클래스 컨버터 사용시 PathVaiable("")써줘야 한다

        //final Optional<Post> byId = postRepository.findById(id);
        //return byId.get();
        return post;
    }

    @GetMapping("/posts")
    public Page<Post> getPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }
}
