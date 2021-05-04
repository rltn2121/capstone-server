//package com.capstone.mountain.repository;
//
//import com.capstone.mountain.domain.QRecord;
//import com.capstone.mountain.domain.QUser;
//import com.capstone.mountain.domain.Record;
//import com.capstone.mountain.domain.User;
//import com.capstone.mountain.dto.UserProfileDto;
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.time.LocalTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//import static com.capstone.mountain.domain.QRecord.record;
//import static com.capstone.mountain.domain.QUser.user;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class UserRepositoryTest {
//
//    @Autowired
//    EntityManager em;
//    @Autowired
//    JPAQueryFactory queryFactory;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void findUser() throws Exception{
//        // given
//        String username = "test2";
//        // when
//        User byUsername = userRepository.findByUsername(username);
//        // then
//        assertThat(byUsername.getUsername()).isEqualTo(username);
//
//    }
//
//    @Test
//    public void 사용자정보변경() throws Exception{
//        // bulk 연산은 영속성 컨텍스트 무시하고 바로 db에 접근
//        // 따라서 update 수행 후 db 상태와 영속성 컨텍스트 상태가 달라짐
//        // given
//        Long userId = 1L;
//        String nickname = "changed nickname";
//        int weight = 54;
//        int height = 176;
//
//        // when
//        long execute = queryFactory.update(user)
//                .set(user.height, height)
//                .set(user.weight, weight)
//                .set(user.nickname, nickname)
//                .where(user.id.eq(userId))
//                .execute();
//
//        em.flush();
//        em.clear();
//        // then
//        User updatedUser = queryFactory.selectFrom(QUser.user)
//                .where(QUser.user.id.eq(userId))
//                .fetchOne();
//        assertThat(updatedUser.getNickname()).isEqualTo(nickname);
//        assertThat(updatedUser.getHeight()).isEqualTo(height);
//        assertThat(updatedUser.getWeight()).isEqualTo(weight);
//    }
//
//    @Test
//    public void 사용자프로필조회() throws Exception{
//        // given
//        Long userId = 100L;
//        // when
//        UserProfileDto userProfile = userRepository.getUserProfile(userId);
//        if(userProfile == null){
//            System.out.println("userprofile is null");
//        }
//        else
//            System.out.println("userprofile is not null");
//    }
//
//}