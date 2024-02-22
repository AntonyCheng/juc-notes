package top.sharehome.demo02;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入锁示例代码
 *
 * @author AntonyCheng
 */

public class Demo02_4 {

    /**
     * 定义可重入锁
     */
    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    /**
     * lock() 方法标准用法
     */
    public static void lockSample() {
        REENTRANT_LOCK.lock();
        try {
            System.out.println("现在执行lock()拿到锁之后的代码，没有执行finally中的代码");
        } finally {
            System.out.println("现在执行finally中的代码，释放锁");
            REENTRANT_LOCK.unlock();
        }
    }

    /**
     * tryLock() 标准用法
     */
    public static void tryLockSample() {
        if (REENTRANT_LOCK.tryLock()) {
            System.out.println("现在执行tryLock()拿到锁之后的代码");
        } else {
            System.out.println("现在执行tryLock()没拿到锁之后的代码");
        }
    }

    /**
     * 方法入口
     */
    public static void main(String[] args) throws InterruptedException {
        // 演示lock()方法
        lockSample();
        // 演示tryLock()方法
        new Thread(Demo02_4::tryLockSample).start();
        tryLockSample();
    }

}
