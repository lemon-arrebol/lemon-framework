package com.lemon.dynamic.dasource.aspect;

import com.lemon.dynamic.dasource.holder.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @param
 * @author lemon
 * @description 动态数据源AOP织入
 * @return
 * @date 2019-08-10 23:56
 */
@Slf4j
public class DynamicDataSourceAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;

    private Pointcut pointcut;

    private final DynamicDataSourceClassResolver dynamicDataSourceClassResolver = new DynamicDataSourceClassResolver();

    public DynamicDataSourceAnnotationAdvisor() {
        this.advice = buildAdvice();
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    /**
     * @param
     * @return
     * @description 构建advice，查找类或方法上的注解，注解可能包含其它的元注解
     * @author lemon
     * @date 2019-08-10 23:57
     */
    private Advice buildAdvice() {
        MethodInterceptor interceptor =
                (invocation) -> {
                    Method method = invocation.getMethod();
                    Class<?> declaringClass = this.dynamicDataSourceClassResolver.targetClass(invocation);
                    AssignDataSource assignDataSource = AnnotationUtils.findAnnotation(method, AssignDataSource.class);

                    if (assignDataSource == null) {
                        assignDataSource = AnnotationUtils.findAnnotation(declaringClass, AssignDataSource.class);
                    }

                    String key = assignDataSource.value();
                    Object oldDataSourceName = DynamicDataSourceContextHolder.peekDataSourceKey();

                    try {
                        DynamicDataSourceContextHolder.pushDataSourceKey(key);

                        if (log.isDebugEnabled()) {
                            log.debug("Thread [{}] switch data source from [{}] to [{}], current data source [{}], method [{}]",
                                    Thread.currentThread().getName(), oldDataSourceName, assignDataSource.value(),
                                    DynamicDataSourceContextHolder.peekDataSourceKey(),
                                    String.format("%s.%s", invocation.getThis().getClass().getName(), method.getName()));
                            DynamicDataSourceContextHolder.printDataSourceKey();
                        }

                        return invocation.proceed();
                    } finally {
                        DynamicDataSourceContextHolder.pollDataSourceKey();

                        if (log.isDebugEnabled()) {
                            log.debug("Thread [{}] reset the data source from [{}] to [{}], current data source [{}], method [{}]",
                                    Thread.currentThread().getName(), assignDataSource.value(), oldDataSourceName,
                                    DynamicDataSourceContextHolder.peekDataSourceKey(),
                                    String.format("%s.%s", invocation.getThis().getClass().getName(), method.getName()));
                            DynamicDataSourceContextHolder.printDataSourceKey();
                        }
                    }
                };
        return interceptor;
    }

    /**
     * @param
     * @return
     * @description 构建pointcut
     * @author lemon
     * @date 2019-08-10 23:57
     */
    private Pointcut buildPointcut() {
        // 匹配类上以及超类使用AssignDataSource注解的类，包括使用注解AssignDataSource的自定义注解
        Pointcut classPointcut = new AnnotationMatchingPointcut(AssignDataSource.class, true);
        // 匹配方法(类上以及超类中的方法，子类中方法可以覆盖父类中方法上注解)使用AssignDataSource注解的类，包括使用注解AssignDataSource的自定义注解
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, AssignDataSource.class, true);
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }

    @Override
    public void setOrder(int order) {
        super.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
