package com.lemon.common.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author lemon
 * @version 1.0
 * @description: 时间工具类
 * @date Create by lemon on 2019-07-21 09:13
 */
@Slf4j
public class TimeUtil {
    /**
     * @param
     * @return
     * @description 指定时间是否在当前时间之前
     * @author lemon
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrentBySecond(long compareTimeSecond) {
        Preconditions.checkArgument(compareTimeSecond > 0, "compareTime not allowed less than 0");

        Instant compareTime = Instant.ofEpochSecond(compareTimeSecond);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(compareTime, ZoneId.systemDefault());

        if (log.isDebugEnabled()) {
            log.debug("Second [{}] Convert to LocalDateTime [{}]", compareTimeSecond, localDateTime);
        }

        return localDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * @param
     * @return
     * @description 指定时间是否在当前时间之前
     * @author lemon
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrentByMilli(long compareTimeMilli) {
        Preconditions.checkArgument(compareTimeMilli > 0, "compareTime not allowed less than 0");

        Instant compareTime = Instant.ofEpochMilli(compareTimeMilli);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(compareTime, ZoneId.systemDefault());

        if (log.isDebugEnabled()) {
            log.debug("Millisecond [{}] Convert to LocalDateTime [{}]", compareTimeMilli, localDateTime);
        }

        return localDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * @param
     * @return
     * @description 指定时间是否在当前时间之前
     * @author lemon
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrent(String compareTime, String pattern) {
        Preconditions.checkArgument(StringUtils.isNotBlank(compareTime), "compareTime not allowed to be empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(pattern), "Time formatter not allowed to be empty");

        DateTimeFormatter jwtExpireTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(compareTime, jwtExpireTimeFormatter);

        if (log.isDebugEnabled()) {
            log.debug("The CompareTime [{}] are formatted according to the pattern [{}] as LocalDateTime [{}]", compareTime, pattern, localDateTime);
        }

        return localDateTime.isBefore(LocalDateTime.now());
    }
}
