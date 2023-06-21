package com.wxw.springbootdemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AopTest {

    /**
     * 切入点：使用 @MyAopAnnotation 注解声明的方法都会被拦截。
     * @ Pointcut： 声明连接点；
     *
     */
    @Pointcut("@annotation(com.wxw.springbootdemo.aop.MyAopAnnotation)")
    public void pointCut() {
    }

    /**
     * 前置通知：在方法执行之前通知
     * @param joinPoint 切点，可以获取到被拦截的方法等信息
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("前置通知，会被拦截的方法为：" + method.getName());
    }

    /**
     * 后置通知：在方法执行之后通知
     * @param joinPoint 切点，可以获取到被拦截的方法等信息
     */
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("后置通知，被拦截的方法 [" + method.getName() + "] 执行完毕！");
    }

    /**
     * 后置返回通知：在方法 返回结果 之后通知
     * @param joinPoint 切点，可以获取到被拦截的方法等信息
     * @param result 被拦截的方法返回的结果
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("后置通知，被拦截的方法 [" + method.getName() + "] 返回了 [" + result.toString() + "]");
    }

    /**
     * 后置异常通知：在方法 抛出异常 之后通知
     * @param joinPoint 切点，可以获取到被拦截的方法等信息
     * @param exception 被拦截的方法抛出的异常
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("后置通知，被拦截的方法 [" + method.getName() + "] 抛出异常 [" + exception.getClass().getSimpleName() + "]");
        if (exception instanceof NullPointerException) {
            System.out.println("空指针异常！");
        }
    }

    /**
     * 环绕通知：围绕方法的执行, proceed()方法是实际调用到被拦截的方法，通知的优先级最高
     * @param proceedingJoinPoint JoinPoint的子类，核心就是 proceed() 方法
     */
    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知 before proceed()：" + proceedingJoinPoint.getSignature().getName());
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("环绕通知 after proceed()：" + proceedingJoinPoint.getSignature().getName());
        return proceed;
    }
}
