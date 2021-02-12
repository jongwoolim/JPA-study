package me.jongwoo.springdata;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void save(){
        Post post = new Post();
        post.setTitle("jpa");
        final Post savedPost = postRepository.save(post); // persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");

        //merge()메소드에 넘긴 그 엔티티의 복사본을 만들고 그 복사본을 Persistent 상태로 변경하고 복사본 반환
        final Post updatedPost = postRepository.save(postUpdate);// merge update 쿼리

        assertThat(entityManager.contains(updatedPost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(postUpdate == updatedPost);

        final List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void querydslTest(){
        Post post = new Post();
        post.setTitle("hibernate");
        postRepository.save(post);

        Predicate qpost = QPost.post.title.containsIgnoreCase("hibernate");
        final Optional<Post> one = postRepository.findOne(qpost);
        assertThat(one).isNotEmpty();

    }


    @Test
    public void event(){
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);


    }

    @Test
    public void crud(){

        Post post = new Post();
        post.setTitle("hibernate");

        //Transient
        assertThat(postRepository.contains(post)).isFalse();
        postRepository.save(post.publish());
        //persist
        assertThat(postRepository.contains(post)).isTrue();

        postRepository.findMyPost();
        postRepository.delete(post);
        postRepository.flush();
    }

    @Test
    @Rollback(false)
    public void crudRepository(){

        //Given
        Post post = new Post();
        post.setTitle("hello spring boot common");
        assertThat(post.getId()).isNull();

        //When
        final Post savedPost = postRepository.save(post);

        //Then
        assertThat(savedPost.getId()).isNotNull();

        //When
        final List<Post> all = postRepository.findAll();

        //Then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all).contains(savedPost);

        //When
        Page<Post> postPage = postRepository.findAll(PageRequest.of(0, 10));

        //Then
        assertThat(postPage.getTotalElements()).isEqualTo(1);
        assertThat(postPage.getNumber()).isEqualTo(0);
        assertThat(postPage.getSize()).isEqualTo(10);
        assertThat(postPage.getNumberOfElements()).isEqualTo(1);


        //When
        postPage  = postRepository.
                findByTitleContains("spring", PageRequest.of(0, 10));

        //Then
        assertThat(postPage.getTotalElements()).isEqualTo(1);
        assertThat(postPage.getNumber()).isEqualTo(0);
        assertThat(postPage.getSize()).isEqualTo(10);
        assertThat(postPage.getNumberOfElements()).isEqualTo(1);

        //When
        final long spring = postRepository.countByTitleContains("spring");
        assertThat(spring).isEqualTo(1);


    }

}