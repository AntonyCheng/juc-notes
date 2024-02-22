package top.sharehome.demo02;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lock接口及其实现类
 * 该接口常用实现类有3个：
 * 1、ReentrantLock 可重入锁
 * 2、ReentrantReadWriteLock.ReadLock 读锁
 * 3、ReentrantReadWriteLock.WriteLock 写锁
 *
 * @author AntonyCheng
 */

public class Demo02_3 {

    public static void main(String[] args) {
        // 可重入锁
        ReentrantLock reentrantLock = new ReentrantLock();
        // 读写锁
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        // 写锁
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    }

}
