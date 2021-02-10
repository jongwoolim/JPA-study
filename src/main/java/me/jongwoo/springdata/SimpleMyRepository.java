package me.jongwoo.springdata;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
public class SimpleMyRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {

    private EntityManager entityManager;

    public SimpleMyRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findMyPost() {
        System.out.println("custom repository");
        return entityManager.createQuery("SELECT p FROM Post As p", Post.class).getResultList();
    }

    @Override
    public void delete(T entity) {
        System.out.println("custom delete");
        entityManager.remove(entity);
    }


    @Override
    public boolean contains(T entity) {

        return entityManager.contains(entity);
    }
}
