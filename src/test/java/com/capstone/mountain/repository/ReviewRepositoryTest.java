package com.capstone.mountain.repository;

import com.capstone.mountain.domain.QCourse;
import com.capstone.mountain.domain.QReview;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.capstone.mountain.domain.QCourse.*;
import static com.capstone.mountain.domain.QReview.review;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {
    @Autowired
    EntityManager em;


    @Test
    public void 평점조회() throws Exception{
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        // given
        Long courseId = 2L;

        // when
        Tuple tuple = queryFactory
                .select(review.score.avg(), review.score.count())
                .from(review)
                .where(course.id.eq(courseId))
                .fetchOne();

        // then
        System.out.println("tuple = " + tuple);
    }
}