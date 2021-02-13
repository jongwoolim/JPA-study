package me.jongwoo.springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = SimpleMyRepository.class)
@EnableJpaAuditing(auditorAwareRef = "accountAuditAware")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
