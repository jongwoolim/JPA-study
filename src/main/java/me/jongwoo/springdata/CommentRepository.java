package me.jongwoo.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment>, QueryByExampleExecutor<Comment> {

//    Comment save(Comment comment);
//    List<Comment> findAll();

    //@Query(value = "SELECT c FROM Comment c")
    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountAsc(String keyword);

    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    //Stream<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    @EntityGraph(attributePaths = "post", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Post> getById(Long id);

    //closed 프로젝션
//    List<CommentSummary> findByPost_Id(Long id);
    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
