package com.lemon.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author houjuntao
 * @version 1.0
 * @description: TODO
 * @date Create by houjuntao on 2019-07-21 09:13
 */
@Slf4j
public class TimeUtil {
    /**
     * @param
     * @return
     * @description 指定时间是否在当前时间之前
     * @author houjuntao
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrentBySecond(long compareTimeSecond) {
        AssertUtil.isTrue(compareTimeSecond > 0, "compareTime not allowed less than 0");

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
     * @author houjuntao
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrentByMilli(long compareTimeMilli) {
        AssertUtil.isTrue(compareTimeMilli > 0, "compareTime not allowed less than 0");

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
     * @author houjuntao
     * @date 2019-07-21 09:20
     */
    public static boolean isBeforeOfCurrent(String compareTime, String pattern) {
        AssertUtil.isTrue(StringUtils.isNotBlank(compareTime), "compareTime not allowed to be empty");
        AssertUtil.isTrue(StringUtils.isNotBlank(pattern), "Time formatter not allowed to be empty");

        DateTimeFormatter jwtExpireTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(compareTime, jwtExpireTimeFormatter);

        if (log.isDebugEnabled()) {
            log.debug("The CompareTime [{}] are formatted according to the pattern [{}] as LocalDateTime [{}]", compareTime, pattern, localDateTime);
        }

        return localDateTime.isBefore(LocalDateTime.now());
    }
}
