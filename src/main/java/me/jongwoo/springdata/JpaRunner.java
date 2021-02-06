package me.jongwoo.springdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Post post = new Post();
        post.setTitle("Spring Data JPA....");

        Comment comment = new Comment();
        comment.setComment("Spring JPA study...!");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("빨리 공부하고 싶어요");
        post.addComment(comment1);

        final Session session = entityManager.unwrap(Session.class);
        session.save(post);




/*        Account account = new Account();
        account.setUsername("jongwoo");
        account.setPassword("hibernate");

        Study study = new Study();
        study.setName("Spring Data JPA");

        account.addStudy(study);

        //이둘은 한 묶음
//        study.setOwner(account);
//        account.getStudies().add(study); //optional

        final Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);
        //entityManager.persist(account);

        //select 쿼리가 발생하지 않는다 1차캐시로 인해 persistent 상태에서 꺼내온다
        final Account jongwoo = session.load(Account.class, account.getId());
        //갹체 변화를 감지하여 update 쿼리를 해준다
        // 더티 체킹(객체 변경 감지)
        // write behind(객체 상태의 변화를 디비에 최대한 늦게 필요한 시점에 적용)
        jongwoo.setUsername("jddung2");
        jongwoo.setUsername("jongwoo2");
        jongwoo.setUsername("jongwoo");
        //객체 변화가 없으므 update 쿼리 발생x
        System.out.println("==============");
        System.out.println(jongwoo.getUsername());

        //insert 쿼리는 트랙잭션 끝나고 날린다*/
    }
}
