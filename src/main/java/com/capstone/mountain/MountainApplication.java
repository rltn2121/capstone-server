package com.capstone.mountain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.servlet.DispatcherServlet;

import javax.persistence.EntityManager;

@SpringBootApplication
public class MountainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountainApplication.class, args);
    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }


//    @Bean
//    DispatcherServlet dispatcherServlet () {
//        DispatcherServlet ds = new DispatcherServlet();
//        ds.setThrowExceptionIfNoHandlerFound(true);
//        return ds;
//    }
}
