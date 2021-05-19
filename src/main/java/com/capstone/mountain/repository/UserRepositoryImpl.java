package com.capstone.mountain.repository;

import com.capstone.mountain.dto.QUserProfileDto;
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
        //List<LocalTime> timeList = getLocalTimes(userId);
        UserProfileDto userProfileDto = queryFactory
                .select(
                        new QUserProfileDto(
                                user.id,
                                user.nickname,
                                user.height,
                                user.weight,
                                user.url,
                                record.distance.sum(),
                                record.distance.avg(),
                                record.distance.max(),
                                record.moving_time_sec.sum().longValue(),
                                record.moving_time_sec.avg(),
                                record.moving_time_sec.max().longValue(),
                                record.total_time_sec.sum().longValue(),
                                record.total_time_sec.avg(),
                                record.total_time_sec.max().longValue(),
                                record.avg_speed.avg(),
                                record.avg_speed.max(),
                                record.avg_pace.avg(),
                                record.avg_pace.max(),
                                record.max_height.max(),
                                record.max_height.avg(),
                                record.total_uphill.sum(),
                                record.total_uphill.avg(),
                                record.total_uphill.max(),
                                record.total_downhill.sum(),
                                record.total_downhill.avg(),
                                record.total_downhill.max(),
                                record.calorie.sum(),
                                record.calorie.avg()
                        )
                )
                .from(record)
                .rightJoin(record.user, user)
                .where(user.id.eq(userId))
                .groupBy(user)
                .fetchOne();

        return userProfileDto;
//        return null;
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

//    private List<LocalTime> getLocalTimes(Long userId) {
//        return queryFactory
//                .select(record.duration)
//                .from(record)
//                .where(record.user.id.eq(userId))
//                .fetch();
//    }
}
