package top.sharehome.demo08;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级示例代码
 *
 * @author AntonyCheng
 */
public class Demo08_2 {

    public static void main(String[] args) {
        // 定义读写锁
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        // 获取写锁
        writeLock.lock();
        System.out.println("已经获取到写锁，整个锁状态变为写锁状态，接下来获取读锁...");
        // 获取读锁
        readLock.lock();
        System.out.println("已经获取到读锁，接下来释放写锁，使整个锁状态只存在读锁...");
        // 释放写锁
        writeLock.unlock();
        System.out.println("已经释放写锁，整个锁状态变为读锁状态，最后释放读锁...");
        // 释放读锁
        readLock.unlock();
        System.out.println("成功从写锁->读锁->无锁，完成锁降级！接下来试试锁升级，即读锁->写锁->无锁...");
        // 如果上述代码均能执行那就说明能够从写锁降级为读锁

        // 获取读锁
        readLock.lock();
        System.out.println("已经获取到读锁，整个锁状态变为读锁状态，接下来获取写锁...");
        // 获取写锁
        writeLock.lock();
        System.out.println("下面就不用再做说明了，因为根本就无法获取到写锁，这个打印操作也就不会执行...");
        // 释放读锁
        readLock.unlock();
        // 释放写锁
        writeLock.unlock();
    }

}
