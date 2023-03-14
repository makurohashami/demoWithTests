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
public class CustomEntityValidationAspect {

    @Pointcut("@annotation(ActivateCustomValidationAnnotations) && within(com.example.demowithtests.service.*)")
    public void callAtAnnotationActivator() {
    }

    @Before("callAtAnnotationActivator()")
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

    @Before("callAtAnnotationActivator()")
    public void makeFormattedName(JoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Employee) {
                for (Field field : arg.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Name.class)) {
                        field.setAccessible(true);
                        Object value = field.get(arg);
                        if (value instanceof String) {
                            field.set(arg, toNameFormat((String) value));
                        }
                    }
                }
            }
        }
    }

    private String toNameFormat(String name) {
        return name.trim().substring(0, 1).toUpperCase() + name.trim().substring(1).toLowerCase();
    }
}
