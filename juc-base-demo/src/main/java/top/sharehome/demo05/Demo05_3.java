package top.sharehome.demo05;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁示例代码
 *
 * @author AntonyCheng
 */

public class Demo05_3 {

    public static void main(String[] args) {
        synchronizedMethod();
        lockMethod();
    }

    /**
     * 用synchronized关键字进行演示
     */
    public static void synchronizedMethod() {
        // 定义锁类
        Object obj = new Object();

        // 进行三层加锁操作
        synchronized (obj) {
            System.out.println("外层");
            synchronized (obj) {
                System.out.println("中层");
                synchronized (obj) {
                    System.out.println("内层");
                }
            }
        }
    }

    /**
     * 用Lock接口进行演示
     */
    public static void lockMethod() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("外层");
            lock.lock();
            try {
                System.out.println("中层");
                lock.lock();
                try {
                    System.out.println("内层");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

}
