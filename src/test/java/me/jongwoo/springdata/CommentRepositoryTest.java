package me.jongwoo.springdata;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRepository.save(comment);

        //When
        final List<Comment> all = commentRepository.findAll();
        final long count = commentRepository.count();

        //Then
        assertThat(all.size()).isEqualTo(1);
        assertThat(count).isEqualTo(1);

        //When
        final Optional<Comment> byId = commentRepository.findById(100L);

        //Then
        assertThat(byId).isEmpty();
        //final Comment comment1 = byId.orElseGet(Comment::new);

        assertThrows(IllegalArgumentException.class, () ->{
            byId.orElseThrow(IllegalArgumentException::new);
        });


    }
}