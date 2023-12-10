package com.spartanullnull.otil.global.aspect;

import java.util.*;
import lombok.extern.slf4j.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Slf4j(topic = "Check Api Performance By Time")
@Aspect
public class ApiPerformanceAspect {

    @Pointcut("execution(* com.spartanullnull.otil.domain.*(..))")
    private void checkTime() {
    }

    /**
     * 각 API 호출 수행시간 측정
     *
     * @param joinPoint 타겟 API
     * @return 측정시간
     * @throws Throwable 예외발생
     */
    @Around("checkTime()")
    public Optional<?> checkIfLoggedUserIsAuthorOfResource(ProceedingJoinPoint joinPoint)
        throws Throwable {
        long start = System.currentTimeMillis();
        Optional<Object> performance = Optional.of(joinPoint.proceed());
        long end = System.currentTimeMillis();

        log.info("targetName : " + joinPoint.getTarget().toString());
        log.info("Performance Time : " + (end - start));
        return performance;
    }
}
