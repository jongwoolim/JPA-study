package me.jongwoo.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends MyRepository<Post,Long>{

    Page<Post> findByTitleContains(String title, Pageable pageable);

    long countByTitleContains(String title);
}
