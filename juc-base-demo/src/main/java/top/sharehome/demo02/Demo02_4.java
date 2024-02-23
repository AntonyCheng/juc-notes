package top.sharehome.demo02;

import java.util.concurrent.locks.Condition;
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
     * 采用lock()，必须主动去释放锁，并且在发生异常时，不会自动释放锁。
     * 因此一般来说，使用Lock必须在try{}catch{}块中进行，并且将释放锁的操作放在finally块中进行，
     * 以保证锁一定被被释放，防止死锁的发生。
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
            try {
                System.out.println("现在执行tryLock()拿到锁之后的代码");
            } finally {
                REENTRANT_LOCK.unlock();
            }
        } else {
            System.out.println("现在执行tryLock()没拿到锁之后的代码");
        }
    }

    /**
     * newCondition() 用法
     * 关键字synchronized与wait()/notify()这两个方法一起使用可以实现等待/通知模式， Lock锁的newContition()方法返回Condition对象，Condition类中await()和signal()也可以实现等待/通知模式。
     * 注意：在调用Condition的await()/signal()方法前，也需要线程持有相关的Lock锁，调用await()后线程会释放这个锁，在singal()调用后会从当前Condition对象的等待队列中，唤醒 一个线程，唤醒的线程尝试获得锁， 一旦获得锁成功就继续执行。
     */
    public static void newConditionSample() {
        Condition condition = REENTRANT_LOCK.newCondition();
        new Thread(() -> {
            REENTRANT_LOCK.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "线程开始等待...");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "线程结束等待...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }, "await").start();
        try {
            // 主线程休眠两秒
            System.out.println(Thread.currentThread().getName() + "线程休眠两秒...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 让主线程获得锁
        REENTRANT_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "线程休眠完成，唤醒等待中的线程...");
            condition.signalAll();
        } finally {
            REENTRANT_LOCK.unlock();
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
        // 演示newCondition()方法
        newConditionSample();
    }

}
