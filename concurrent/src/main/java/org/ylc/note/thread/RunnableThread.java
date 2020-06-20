package org.ylc.note.thread;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 实现 Runnable 接口
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/20
 */
public class RunnableThread implements Runnable {

    @Override
    public void run() {
        System.out.println("实现Runnable接口：" + Thread.currentThread().getName());
    }

}
