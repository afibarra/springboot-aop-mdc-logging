package me.afibarra.mdc.aop;

import java.lang.reflect.Method;
import me.afibarra.mdc.annotations.MdcMethod;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MdcContextAop {

    private static final String MESSAGE_ID = "messageid";

    @Around("classAnnotatedWithMdc()")
    public Object aroundAnnotatedClass(ProceedingJoinPoint joinPoint) throws Throwable {
        setMdcClass(joinPoint);

        return joinPoint.proceed();
    }

    @Around("methodAnnotatedWithMdc()")
    public Object aroundAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        setMdcMethod(joinPoint);

        return joinPoint.proceed();
    }

    @Pointcut("@within(me.afibarra.mdc.annotations.MdcClass)")
    private void classAnnotatedWithMdc() {
    }

    @Pointcut("@annotation(me.afibarra.mdc.annotations.MdcMethod)")
    public void methodAnnotatedWithMdc() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setMdcClass(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
//        MdcClass annotation = (MdcClass) clazz.getAnnotation(MdcClass.class);
//
//        String functionName = annotation.methodName();
//
//        if (StringUtils.isBlank(functionName)) {
//            functionName =
//                getClassName(signature.getDeclaringTypeName()) + "." + signature.getMethod()
//                    .getName();
//        }

//        MDC.put(MESSAGE_ID, functionName);
        MDC.put(MESSAGE_ID, "MDC: " + clazz.getName() + "." + signature.getMethod().getName());
    }

    private void setMdcMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MdcMethod annotation = method.getAnnotation(MdcMethod.class);

        String functionName = annotation.methodName();

        if (StringUtils.isBlank(functionName)) {
            functionName = getClassName(signature.getDeclaringTypeName()) + "." + method.getName();
        }

        MDC.put(MESSAGE_ID, functionName);
    }

    private String getClassName(String classFullName) {
        int startIndexOfClassName = StringUtils.lastIndexOf(classFullName, ".") + 1;
        return StringUtils.substring(classFullName, startIndexOfClassName);
    }
}
