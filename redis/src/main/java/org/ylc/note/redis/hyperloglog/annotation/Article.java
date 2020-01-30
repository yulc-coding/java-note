package org.ylc.note.redis.hyperloglog.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 文章统计信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Article {

    /**
     * 值为对应HyperLogLog的key
     */
    String value() default "";
}
