package me.jongwoo.springdata;

import javax.persistence.*;
import java.util.Date;

@Entity
@SuppressWarnings("ALL")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String email;

    @Temporal(TemporalType.TIME)
    private Date created = new Date();

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "street", column = @Column(name="home_street"))
    )
    private Address address;

    private String yes;

    @Transient
    private String no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
