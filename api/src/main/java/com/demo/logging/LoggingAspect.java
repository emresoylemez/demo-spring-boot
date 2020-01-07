package com.demo.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.demo.logging.Markers.ENTER;
import static com.demo.logging.Markers.EXCEPTION;
import static com.demo.logging.Markers.EXIT;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
public class LoggingAspect {


    private final Logger logger = LoggerFactory.getLogger("aop");

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("( within(com.demo.service.*) && !within(com.demo.service.IdentityService*) )" +
            " || within(com.demo.client..*)" +
            " || within(com.demo.controller.*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        logger.error(EXCEPTION, "Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e);
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(ENTER, "{} with argument[s] = {}",
                joinPoint.getSignature().toShortString(),
                getAnnotatedParametersMasked(joinPoint));

        try {
            Object result = joinPoint.proceed();
            logger.info(EXIT, "{} with result = {}",
                    joinPoint.getSignature().toShortString(),
                    result);
            return result;
        } catch (IllegalArgumentException e) {
            logger.error(EXCEPTION, "Illegal argument: {} in {}.{}()",
                    getAnnotatedParametersMasked(joinPoint),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());

            throw e;
        }
    }

    private String getAnnotatedParametersMasked(final ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (MethodArgument argument : MethodArgument.asList(joinPoint)) {
            sb.append(argument.getName());

            if (argument.getValue() == null) {
                sb.append("=null ");
            } else if (argument.hasAnnotation(MaskInLogs.class)) {
                sb.append("=XXX ");
            } else {
                sb.append("=");
                sb.append(argument.getValue());
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}
