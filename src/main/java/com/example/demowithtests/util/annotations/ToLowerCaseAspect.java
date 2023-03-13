package com.example.demowithtests.util.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.FieldSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Aspect
public class ToLowerCaseAspect {

    @AfterReturning(value = "execution(* *(.., @ToLowerCase (*), ..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        if (result == null) {
            return;
        }
        FieldSignature signature = (FieldSignature) joinPoint.getSignature();
        Field field = signature.getField();
        ToLowerCase annotation = field.getAnnotation(ToLowerCase.class);
        if (annotation != null) {
            field.setAccessible(true);
            Object value = field.get(result);
            if (value instanceof String) {
                field.set(result, ((String) value).toLowerCase());
            }
        }
    }
}
