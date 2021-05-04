//package com.capstone.mountain.repository;
//
//import com.capstone.mountain.domain.*;
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.capstone.mountain.domain.QCourse.*;
//import static com.capstone.mountain.domain.QReview.review;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class ReviewRepositoryTest {
//    @Autowired
//    ReviewRepository reviewRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    CourseRepository courseRepository;
//    @Autowired
//    EntityManager em;
//
//@Test
//public void 리뷰찾기() throws Exception{
//    // given
//    Long id = 1L;
//    User user = userRepository.findById(id).get();
//
//    // when
//
//    List<Review> byUserid = reviewRepository.findByUser(user);
//
//    for (Review review1 : byUserid) {
//        System.out.println("review1 = " + review1);
//    }
//}
//
//@Test
//public void 찾기2() throws Exception{
//    // given
//    Long id = 2L;
//    // when
//    Course course = courseRepository.findById(id).get();
//    // then
//
//    List<Review> byCourse = reviewRepository.findByCourse(course);
//    for (Review review1 : byCourse) {
//        System.out.println("review1 = " + review1);
//    }
//}
//    @Test
//    public void 평점조회() throws Exception{
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        // given
//        Long courseId = 2L;
//
//        // when
//        Tuple tuple = queryFactory
//                .select(review.score.avg(), review.score.count())
//                .from(review)
//                .where(course.id.eq(courseId))
//                .fetchOne();
//
//        // then
//        System.out.println("tuple = " + tuple);
//    }
//}