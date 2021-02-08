package me.jongwoo.springdata;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface MyRepository<T, Id extends Serializable> extends Repository<T, Id> {

   <E extends T>E save(E comment);

   List<T> findAll();

   long count();
}
