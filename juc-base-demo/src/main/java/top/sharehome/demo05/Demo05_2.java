package top.sharehome.demo05;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁演示
 *
 * @author AntonyCheng
 */

public class Demo05_2 {

    public static void main(String[] args) {
        Demo05_2FairLock fairLock = new Demo05_2FairLock();
        new Thread(() -> {
            while (true) {
                fairLock.method();
            }
        }, "Thread01").start();
        new Thread(() -> {
            while (true) {
                fairLock.method();
            }
        }, "Thread02").start();
        new Thread(() -> {
            while (true) {
                fairLock.method();
            }
        }, "Thread03").start();
    }

}

/**
 * 公平锁资源类，将默认为1的数字自增为30
 */
class Demo05_2FairLock {

    /**
     * 定义默认数字
     */
    private int defaultNum = 1;

    /**
     * 定义一个ReentrantLock锁，添加true构造参数，即可创建一个公平锁
     */
    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     * 多线程方法
     */
    public void method() {
        lock.lock();
        try {
            if (defaultNum < 30) {
                System.out.println(Thread.currentThread().getName() + "：现在是" + (defaultNum++) + ",自增之后是" + defaultNum);
            }
        } finally {
            lock.unlock();
        }
    }

}
