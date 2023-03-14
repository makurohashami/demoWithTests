package com.example.demowithtests.util.annotations;

import com.example.demowithtests.domain.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class ToLowerCaseAspect {

    @Pointcut("@annotation(ActivateToLowerCase) && within(com.example.demowithtests.service.*)")
    public void callAtAnnotationActivateToLowerCase() {
    }

    @Before("callAtAnnotationActivateToLowerCase()")
    public void makeToLowerCase(JoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Employee) {
                for (Field field : arg.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(ToLowerCase.class)) {
                        field.setAccessible(true);
                        Object value = field.get(arg);
                        if (value instanceof String) {
                            field.set(arg, ((String) value).toLowerCase());
                        }
                    }
                }
            }
        }
    }
}
