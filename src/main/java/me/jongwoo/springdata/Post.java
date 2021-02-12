package me.jongwoo.springdata;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post extends AbstractAggregateRoot<Post> {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

//    @OneToMany(mappedBy = "post",cascade = CascadeType.PERSIST)
//    private Set<Comment> comments = new HashSet<>();


//    public void addComment(Comment comment){
//        this.getComments().add(comment);
//        comment.setPost(this);
//    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Set<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(Set<Comment> comments) {
//        this.comments = comments;
//    }

    public Post publish() {
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
