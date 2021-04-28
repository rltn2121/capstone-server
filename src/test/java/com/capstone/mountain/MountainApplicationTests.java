package com.capstone.mountain;

import com.capstone.mountain.domain.QUser;
import com.capstone.mountain.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.capstone.mountain.domain.QUser.*;

@SpringBootTest
@Transactional

class MountainApplicationTests {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Test
    void contextLoads() {
        User user = new User();
        em.persist(user);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QUser qUser = new QUser("h");

        User result = query
                .selectFrom(qUser)
                .fetchOne();

        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    public void 동적쿼리_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer weightParam = 10;
        List<User> result = searchMember2(usernameParam, weightParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
    private List<User> searchMember2(String usernameCond, Integer weightCond) {
        return queryFactory
                .selectFrom(user)
                .where(usernameEq(usernameCond), weightEq(weightCond))
                .fetch();
    }
    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? user.username.eq(usernameCond) : null;
    }
    private BooleanExpression weightEq(Integer weightCond) {
        return weightCond != null ? user.weight.eq(weightCond) : null;
    }

}
