package com.lemon.dynamic.dasource.aspect;

import com.lemon.dynamic.dasource.holder.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * @author houjuntao
 * @version 1.0
 * @description: 数据源切换
 * @date Create by houjuntao on 2019-08-10 16:43
 */
@Slf4j
@Aspect
@Order(-100)
public class DynamicDataSourceAspect {
    /**
     * @param
     * @return
     * @description
     * @author houjuntao
     * @date 2019-08-10 23:17
     */
    @Around("@annotation(assignDataSource)")
    public Object changeDataSource(ProceedingJoinPoint joinPoint, AssignDataSource assignDataSource) throws Throwable {
        Object oldDataSourceName = DynamicDataSourceContextHolder.peekDataSourceKey();
        DynamicDataSourceContextHolder.pushDataSourceKey(assignDataSource.value());

        if (log.isDebugEnabled()) {
            log.debug("Switch data source from [{}] to [{}], method [{}]", oldDataSourceName, assignDataSource.value(), joinPoint.getSignature());
        }

        Object result;

        try {
            result = joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.pollDataSourceKey();

            if (log.isDebugEnabled()) {
                log.debug("Reset the data source from [{}] to [{}], method [{}]", oldDataSourceName, assignDataSource.value(), joinPoint.getSignature());
            }
        }

        return result;
    }
}
