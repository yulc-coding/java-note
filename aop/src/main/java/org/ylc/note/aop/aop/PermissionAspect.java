package org.ylc.note.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ylc.note.aop.annotation.VisitPermission;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * aop 验证权限
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/19
 */
@Aspect
@Component
public class PermissionAspect {

    /**
     * 切入点
     * 切入点为包路径下的：execution(public * org.ylc.note.aop.controller..*(..))：
     * org.ylc.note.aop.Controller包下任意类任意返回值的 public 的方法
     * <p>
     * 切入点为注解的： @annotation(VisitPermission)
     * 存在 VisitPermission 注解的方法
     */
    @Pointcut("@annotation(org.ylc.note.aop.annotation.VisitPermission)")
    private void permission() {

    }

    /**
     * 目标方法调用之前执行
     */
    @Before("permission()")
    public void doBefore() {
        System.out.println("================== step 2: before ==================");
    }

    /**
     * 目标方法调用之后执行
     */
    @After("permission()")
    public void doAfter() {
        System.out.println("================== step 4: after ==================");
    }

    /**
     * 环绕
     * 会将目标方法封装起来
     * 具体验证业务数据
     */
    @Around("permission()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("================== step 1: around ==================");
        long startTime = System.currentTimeMillis();
        /*
         * 获取当前http请求中的token
         * 解析token :
         * 1、token是否存在
         * 2、token格式是否正确
         * 3、token是否已过期（解析信息或者redis中是否存在）
         * */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("非法请求，无效token");
        }
        // 校验token的业务逻辑
        // ...

        /*
         * 获取注解的值，并进行权限验证:
         * redis 中是否存在对应的权限
         * redis 中没有则从数据库中获取权限
         * 数据空中也没有，抛异常，非法请求，没有权限
         * */
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        VisitPermission visitPermission = method.getAnnotation(VisitPermission.class);
        String value = visitPermission.value();
        // 校验权限的业务逻辑
        // List<Object> permissions = redis.get(permission)
        // db.getPermission
        // permissions.contains(value)
        // ...
        System.out.println(value);

        // 执行具体方法
        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        /*
         * 记录相关执行结果
         * 可以存入MongoDB 后期做数据分析
         * */
        // 打印请求 url
        System.out.println("URL            : " + request.getRequestURL().toString());
        // 打印 Http method
        System.out.println("HTTP Method    : " + request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        System.out.println("controller     : " + proceedingJoinPoint.getSignature().getDeclaringTypeName());
        // 调用方法
        System.out.println("Method         : " + proceedingJoinPoint.getSignature().getName());
        // 执行耗时
        System.out.println("cost-time      : " + (endTime - startTime) + " ms");

        return result;
    }
}
