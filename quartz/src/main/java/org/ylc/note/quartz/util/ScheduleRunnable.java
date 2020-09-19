package org.ylc.note.quartz.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 执行定时任务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-19
 */
@Slf4j
public class ScheduleRunnable<T> implements Runnable {

    /**
     * 执行类
     */
    private final Object target;

    /**
     * 执行方法
     */
    private final Method method;

    /**
     * 执行参数
     */
    private final String params;

    public ScheduleRunnable(Class<T> clazz, String methodName, String params) throws NoSuchMethodException {
        this.target = SpringContextUtil.getBean(clazz);
        this.params = params;
        if (StringUtils.isEmpty(params)) {
            this.method = clazz.getDeclaredMethod(methodName);
        } else {
            this.method = clazz.getDeclaredMethod(methodName, String.class);
        }
    }

    @Override
    public void run() {
        try {
            if (StringUtils.isEmpty(params)) {
                method.invoke(target);
            } else {
                method.invoke(target, params);
            }
        } catch (Exception e) {
            log.error("执行定时任务失败：{}", e.getMessage());
        }

    }
}
