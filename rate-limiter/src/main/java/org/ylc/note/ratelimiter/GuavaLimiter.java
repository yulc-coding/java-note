package org.ylc.note.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/19
 */
public class GuavaLimiter {

    public void limiter() throws InterruptedException {
        // 一秒只能处理10个并发请求
        RateLimiter rateLimiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int taskNu = 10;
        CountDownLatch countDownLatch = new CountDownLatch(taskNu);
        long start = System.currentTimeMillis();
        for (int i = 0; i < taskNu; i++) {
            final int j = i;
            executorService.submit(
                    () -> {
                        rateLimiter.acquire(1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " gets job " + j + " done");
                        countDownLatch.countDown();
                    }
            );
        }
        executorService.shutdown();
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("10 jobs gets done by 5 threads concurrently in " + (end - start) + " milliseconds");
    }


    public static void main(String[] args) throws InterruptedException {
        GuavaLimiter guavaLimiter = new GuavaLimiter();
        guavaLimiter.limiter();
    }

}
