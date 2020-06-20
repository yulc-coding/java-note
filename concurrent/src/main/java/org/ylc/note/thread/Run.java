package org.ylc.note.thread;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/20
 */
public class Run {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RunnableThread thread = new RunnableThread();
            thread.run();
        }
    }
}
