package com.example.demo.aop;//package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoCommonAspect {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.demo.service..*(..))")
    private void doExecute() {}


    @Around("doExecute()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("In dologging");

        String methodName = joinPoint.getSignature().toShortString();

        try {
            //System.out.println("데이터:"+(String)joinPoint.getArgs()[0]);
            log.info(methodName+" is start");
            Object obj = joinPoint.proceed();
            return obj;
        }finally {
            log.info(methodName + " is Finish");
        }
    }
}
