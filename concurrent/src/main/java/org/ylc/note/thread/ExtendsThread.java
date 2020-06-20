package org.ylc.note.thread;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 继承 Thread 类
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/20
 */
public class ExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("继承Thread类：" + Thread.currentThread().getName());
    }
}
