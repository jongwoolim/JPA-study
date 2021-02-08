package me.jongwoo.springdata;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud(){
        //Given
        this.createComment(100, "spring data jpa");
        this.createComment(55, "hibernate SPRING");
        final PageRequest pageable = PageRequest.of(0, 10, Sort.by("likeCount").descending());

        //When
        final List<Comment> all = commentRepository.findAll();
        final long count = commentRepository.count();

        //Then
        assertThat(all.size()).isEqualTo(2);
        assertThat(count).isEqualTo(2);

        //When
        final Optional<Comment> byId = commentRepository.findById(100L);

        //Then
        assertThat(byId).isEmpty();
        //final Comment comment1 = byId.orElseGet(Comment::new);

        assertThrows(IllegalArgumentException.class, () ->{
            byId.orElseThrow(IllegalArgumentException::new);
        });

        //When
        final List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountAsc("spring");
        //Then
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 55);

        //When
        final Page<Comment> spring = commentRepository.findByCommentContainsIgnoreCase("spring", pageable);
        //Then
        assertThat(spring.getNumberOfElements()).isEqualTo(2);
        assertThat(spring).first().hasFieldOrPropertyWithValue("likeCount", 100);

    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);
        commentRepository.save(newComment);
    }
}