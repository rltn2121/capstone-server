package com.capstone.mountain.repository;

import com.capstone.mountain.dto.UserProfileDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import java.time.LocalTime;
import java.util.List;

import static com.capstone.mountain.domain.QRecord.record;
import static com.capstone.mountain.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public Long updateProfile(Long userId, String nickname, int height, int weight) {
        long execute = queryFactory.update(user)
                .set(user.height, height)
                .set(user.weight, weight)
                .set(user.nickname, nickname)
                .where(user.id.eq(userId))
                .execute();

        em.flush();
        em.clear();
        return execute;
    }

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        List<LocalTime> timeList = getLocalTimes(userId);
        Tuple tuple = queryFactory
                .select(
                        user.id,
                        user.nickname,
                        user.weight,
                        user.height,
                        record.distance.sum(),
                        record.distance.avg(),
                        record.height.sum(),
                        record.height.max(),
                        record.height.avg(),
                        record.speed.avg(),
                        record.speed.max(),
                        record.calorie.sum(),
                        record.calorie.avg()
                )
                .from(record)
                .rightJoin(record.user, user)
                .where(user.id.eq(userId))
                .groupBy(user)
                .fetchOne();

        return new UserProfileDto(tuple, getDuration(timeList, "sum"), getDuration(timeList, "avg"));
    }

    private String getDuration(List<LocalTime> timeList, String cmd) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        if(timeList.size() > 0){

            for (LocalTime t : timeList) {
                second += t.getHour()*3600;
                second += t.getMinute()*60;
                second += t.getSecond();
            }
            if(cmd == "avg"){
                second /= timeList.size();
            }

            hour = second / 3600;
            second %= 3600;
            minute = second / 60;
            second %= 60;

        }
        return hour + "시간 " + minute + "분 " + second + "초";
    }

    private List<LocalTime> getLocalTimes(Long userId) {
        System.out.println("getLocalTimes 실행됨");
        return queryFactory
                .select(record.duration)
                .from(record)
                .where(record.user.id.eq(userId))
                .fetch();
    }
}
