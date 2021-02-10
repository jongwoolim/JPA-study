package me.jongwoo.springdata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

   <E extends T>E save(E comment);

   List<T> findAll();

   long count();

   //<E extends T> Optional<E> findById(ID id);

   boolean contains(T entity);


   List<Post> findMyPost();

   void delete(T entity);


}
