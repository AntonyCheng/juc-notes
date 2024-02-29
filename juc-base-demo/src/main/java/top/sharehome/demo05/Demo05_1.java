package top.sharehome.demo05;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 非公平锁演示
 *
 * @author AntonyCheng
 */

public class Demo05_1 {

    public static void main(String[] args) {
        Demo05_1NoFairLock noFairLock = new Demo05_1NoFairLock();
        new Thread(()->{
            while (true){
                noFairLock.method();
            }
        }, "Thread01").start();
        new Thread(()->{
            while (true){
                noFairLock.method();
            }
        }, "Thread02").start();
        new Thread(()->{
            while (true){
                noFairLock.method();
            }
        }, "Thread03").start();
    }

}

/**
 * 非公平锁资源类，将默认为1的数字自增为15
 */
class Demo05_1NoFairLock {

    /**
     * 定义默认数字
     */
    private int defaultNum = 1;

    /**
     * 定义一个ReentrantLock锁，默认就是非公平锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 多线程方法
     */
    public void method() {
        lock.lock();
        try {
            if (defaultNum < 15) {
                System.out.println(Thread.currentThread().getName() + "：现在是" + (defaultNum++) + ",自增之后是" + defaultNum);
            }
        } finally {
            lock.unlock();
        }
    }

}
