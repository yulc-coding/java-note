package org.ylc.note.redis.hyperloglog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ylc.note.redis.hyperloglog.annotation.Article;
import org.ylc.note.redis.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 文章统计AOP
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-30
 */
@Aspect
@Component
public class ArticleAop {

    private static final String PV_PREFIX = "PV:";

    private static final String UV_PREFIX = "UV:";

    private final RedisService redisService;

    public ArticleAop(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(org.ylc.note.redis.hyperloglog.annotation.Article)")
    private void statistics() {
    }

    @Around("statistics()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取注解
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Article visitPermission = method.getAnnotation(Article.class);
        String value = visitPermission.value();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 这里用来模拟，直接通过参数传入。实际项目中可以根据token或者cookie来实现
        String userId = request.getParameter("userId");

        // 热度
        redisService.incr(PV_PREFIX + value);
        // 用户量
        redisService.pfAdd(UV_PREFIX + value, userId);

        // 执行具体方法
        return proceedingJoinPoint.proceed();
    }
}
