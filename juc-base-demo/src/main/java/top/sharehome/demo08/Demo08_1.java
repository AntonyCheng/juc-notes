package top.sharehome.demo08;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁示例代码
 * 使用 ReentrantReadWriteLock 对一个 HashMap 进行读和写操作
 *
 * @author AntonyCheng
 */
public class Demo08_1 {

    public static void main(String[] args) {
        Demo08_1ReadWriteLock demo081ReadWriteLock = new Demo08_1ReadWriteLock();
        // 多线程写
        for (int i = 0; i < 5; i++) {
            final String num = String.valueOf(i);
            new Thread(() -> {
                demo081ReadWriteLock.put(num, "value " + num);
            }, "Write" + (i + 1)).start();
        }
        // 多线程读
        for (int i = 0; i < 5; i++) {
            final String num = String.valueOf(i);
            new Thread(() -> {
                demo081ReadWriteLock.get(num);
            }, "Read" + (i + 1)).start();
        }
    }

}

/**
 * 定义读写锁资源类
 */
class Demo08_1ReadWriteLock {
    /**
     * 定义 HashMap
     */
    private volatile HashMap<String, String> hashMap = new HashMap<>();

    /**
     * 定义读写锁
     */
    private final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock READ_LOCK = READ_WRITE_LOCK.readLock();
    private final ReentrantReadWriteLock.WriteLock WRITE_LOCK = READ_WRITE_LOCK.writeLock();

    /**
     * 定义写操作
     */
    public void put(String key, String value) {
        // 获取写锁
        WRITE_LOCK.lock();
        System.out.println(Thread.currentThread().getName() + "：已经获取写锁，开始写操作...");
        // 模拟读操作需要1s
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "：写操作完毕！");
        // 释放写锁
        WRITE_LOCK.unlock();
    }

    /**
     * 定义读操作
     */
    public void get(String key) {
        // 获取读锁
        READ_LOCK.lock();
        System.out.println(Thread.currentThread().getName() + "：已经获取读锁，开始读操作...");
        // 模拟读操作需要0.5s
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "：读操作完毕！");
        // 释放读锁
        READ_LOCK.unlock();
    }
}