//package com.capstone.mountain.service;
//
//import com.capstone.mountain.repository.MountainRepository;
//import com.querydsl.core.QueryFactory;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class MountainServiceTest {
//    @Autowired
//    private MountainService mountainService;
//    @Autowired
//    private MountainRepository mountainRepository;
//    @Autowired
//    private JPAQueryFactory queryFactory;
//
////    @Test
////    public void 거리구하기() throws Exception{
////        // given
////        double mylat = 34.976956653660075;
////        double mylon = 128.33237146155233;
////
////        double distlat = 34.98135373769556;
////        double distlon = 128.3117727387361;
////        // when
////
////        BooleanExpression result = mountainRepository.test(mylat, mylon, distlat, distlon);
////        System.out.println("dist = " + dist);
////        // then
////    }
//
//}