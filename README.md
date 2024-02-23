# JUC高并发编程（基础）

## JUC概述

### JUC简介

在 Java 中，线程部分是一个重点，本篇文章说的JUC也是关于线程的。JUC就是 `java.util .concurrent`工具包的简称。这是一个处理线程的工具包，JDK 1.5 开始出现的。

![image-20240220200930926](./assets/image-20240220200930926.png)

### 进程与线程

**进程（Process）** 

进程是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。 在当代面向线程设计的计算机结构中，进程是线程的容器。程序是指令、数据及其组织形式的描述，进程是程序的实体。是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。程序是指令、数据及其组织形式的描述，进程是程序的实体。

**线程（thread）** 

线程是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务。

**总结**

- 进程：指在系统中正在运行的一个应用程序；程序一旦运行就是进程；进程——资源分配的最小单位。
- 线程：系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。线程——程序执行的最小单位。

### 线程的状态

#### 线程状态枚举类

该枚举中包含了线程的状态；

```java
public enum State {
    /**
     * Thread state for a thread which has not yet started.
     */
    NEW, // 新建

    /**
     * Thread state for a runnable thread.  A thread in the runnable
     * state is executing in the Java virtual machine but it may
     * be waiting for other resources from the operating system
     * such as processor.
     */
    RUNNABLE, // 准备就绪

    /**
     * Thread state for a thread blocked waiting for a monitor lock.
     * A thread in the blocked state is waiting for a monitor lock
     * to enter a synchronized block/method or
     * reenter a synchronized block/method after calling
     * {@link Object#wait() Object.wait}.
     */
    BLOCKED, // 阻塞

    /**
     * 不见不散
     * Thread state for a waiting thread.
     * A thread is in the waiting state due to calling one of the
     * following methods:
     * <ul>
     *   <li>{@link Object#wait() Object.wait} with no timeout</li>
     *   <li>{@link #join() Thread.join} with no timeout</li>
     *   <li>{@link LockSupport#park() LockSupport.park}</li>
     * </ul>
     *
     * <p>A thread in the waiting state is waiting for another thread to
     * perform a particular action.
     *
     * For example, a thread that has called <tt>Object.wait()</tt>
     * on an object is waiting for another thread to call
     * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
     * that object. A thread that has called <tt>Thread.join()</tt>
     * is waiting for a specified thread to terminate.
     */
    WAITING, // 等待

    /**
     * 过时不候
     * Thread state for a waiting thread with a specified waiting time.
     * A thread is in the timed waiting state due to calling one of
     * the following methods with a specified positive waiting time:
     * <ul>
     *   <li>{@link #sleep Thread.sleep}</li>
     *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
     *   <li>{@link #join(long) Thread.join} with timeout</li>
     *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
     *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
     * </ul>
     */
    TIMED_WAITING, // 定时等待

    /**
     * Thread state for a terminated thread.
     * The thread has completed execution.
     */
    TERMINATED; // 终止
}
```

#### wait/sleep的区别

（1）`sleep()` 是 Thread 类的静态方法，`wait()` 是 Object 类的方法，任何对象实例都能调用。

（2）`sleep()` 不会释放锁，它也不需要占用锁。`wait()` 会释放锁，但调用它的前提是当前线程占有锁(即代码要在 `synchronized` 中)。

（3）它们都可以被 `interrupted` 方法中断。

### 并发与并行

#### 串行

串行表示所有任务都一一按先后顺序进行。串行意味着必须先装完一车柴才能运送这车柴，只有运送到了，才能卸下这车柴，并且只有完成了这整个三个步骤，才能进行下一个步骤。

**串行是一次只能取得一个任务，并执行这个任务。**

#### 并行

并行意味着可以同时取得多个任务，并同时去执行所取得的这些任务。并行模式相当于将长长的一条队列，划分成了多条短队列，所以并行缩短了任务队列的长度。并行的效率从代码层次上强依赖于多进程/多线程代码，从硬件角度上则依赖于多核CPU。

#### 并发

**并发（concurrent）指的是多个程序可以同时运行的现象，更细化的是多进程可以同时运行或者多指令可以同时运行。**但这不是重点，在描述并发的时候也不会去扣这种字眼是否精确，==并发的重点在于它是一种现象，并发描述的是多进程同时运行的现象==。但实际上，对于单核心CPU来说，同一时刻只能运行一个线程。所以，这里的"同时运行"表示的不是真的同一时刻有多个线程运行的现象，这是并行的概念，而是提供一种功能让用户看来多个程序同时运行起来了，但实际上这些程序中的进程不是一直霸占CPU的，而是执行一会停一会。

**要解决大并发问题，通常是将大任务分解成多个小任务**，由于操作系统对进程的调度是随机的，所以切分成多个小任务后，可能会从任一小任务处执行。这可能会出现一些现象：

- 可能出现一个小任务执行了多次，还没开始下个任务的情况。这时一般会采用队列或类似的数据结构来存放各个小任务的成果。
- 可能出现还没准备好第一步就执行第二步的可能。这时，一般采用多路复用或异步的方式，比如只有准备好产生了事件通知才执行某个任务。
- 可以多进程/多线程的方式并行执行这些小任务。也可以单进程/单线程执行这些小任务，这时很可能要配合多路复用才能达到较高的效率。

#### 小结（重点）

**并发：**同一时刻多个线程在访问同一个资源，多个线程对一个点，比如：春运抢票，电商秒杀......

**并行：**多项工作一起执行，之后再汇总，比如：泡方便面，一边电水壶烧水，一边撕调料倒入桶中。

### 管程

管程(monitor)是保证了同一时刻只有一个进程在管程内活动,即管程内定义的操作在同一时刻只被一个进程调用(由编译器实现).但是这样并不能保证进程以设计的顺序执行。

JVM中同步是基于进入和退出管程(monitor)对象实现的，每个对象都会有一个管程(monitor)对象，管程(monitor)会随着java对象一同创建和销毁。

执行线程首先要持有管程对象，然后才能执行方法，当方法完成之后会释放管程，方法在执行时候会持有管程，其他线程无法再获取同一个管程。

用户线程和守护线程

### 用户线程和守护线程

用户线程：平时用到的普通线程，自定义线程。

守护线程：运行在后台，是一种特殊的线程，比如垃圾回收。

当主线程结束后，用户线程还在运行，JVM存活，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo01/Demo01_1.java)如下：

```java
/**
 * 用户线程和主线程
 * 如果用户线程还在进行，JVM就一直存活不终止
 *
 * @author AntonyCheng
 */
public class Demo01_1 {

    public static void main(String[] args) {
        // 创建用户线程，即普通线程
        Thread thread = new Thread(() -> {
            // 使用 Thread.isDaemon() 判断线程是否是守护线程，不是守护线程就是用户线程
            System.out.println("这是" + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程") + ":" + Thread.currentThread().getName());
            // 让用户线程死循环
            for (; ; ) {

            }
        }, "user");
        // 启动用户线程
        thread.start();
        // 主线程打印
        System.out.println("这是主线程:" + Thread.currentThread().getName());
    }

}
```

如果除主线程外没有用户线程，且都是守护线程，即使守护线程还在允许，JVM依旧结束，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo01/Demo01_2.java)如下：

```java
/**
 * 守护线程和主线程
 * 如果只有守护线程还在进行，一旦主线程终止，JVM就会终止
 *
 * @author AntonyCheng
 */
public class Demo01_2 {

    public static void main(String[] args) {
        // 创建守护线程，即普通线程
        Thread thread = new Thread(() -> {
            // 使用 Thread.isDaemon() 判断线程是否是守护线程，不是守护线程就是用户线程
            System.out.println("这是" + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程") + ":" + Thread.currentThread().getName());
            // 让守护线程死循环
            for (; ; ) {

            }
        }, "daemon");
        // 将thread线程转换为守护线程
        thread.setDaemon(true);
        // 启动守护线程
        thread.start();
        // 主线程打印
        System.out.println("这是主线程:" + Thread.currentThread().getName());
    }

}
```

## Lock接口

### Synchronized

#### Synchronized关键字回顾

synchronized是Java中的关键字，是一种同步锁。它修饰的对象有几种，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo02/Demo02_1.java)如下：

（1）修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象。

```java
/**
 * 修饰代码块
 */
public void syncCodeBlock() {
    synchronized (this) {
        System.out.println("同步代码块...");
    }
}
```

（2）修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象。注意：虽然可以使用synchronized来定义方法，但synchronized并不属于方法定义的一部分，因此，synchronized关键字不能被继承。如果在父类中的某个方法使用了synchronized关键字，而在子类中覆盖了这个方法，在子类中的这个方法默认情况下并不是同步的，而必须显式地在子类的这个方法中加上synchronized关键字才可以。当然，还可以在子类方法中调用父类中相应的方法，这样虽然子类中的方法不是同步的，但子类调用了父类的同步方法，因此，子类的方法也就相当于同步了。

```java
/**
 * 修饰方法
 */
public synchronized void syncMethod() {
    System.out.println("同步方法...");
}
```

（3）修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象。

```java
/**
 * 修饰静态方法
 */
public static synchronized void syncStaticMethod() {
    System.out.println("同步静态方法...");
}
```

#### Synchronized编程案例

最初级的多线程编程步骤如下：

1. 确定共享资源，并且创建资源类，在该类中创建属性和操作方法。
2. 创建多线程，调用上述资源类的操作方法去操作共享资源。

案例：出售门票，有3个售票员，一共有30张票，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo02/Demo02_2.java)如下：

```java
/**
 * Synchronized编程案例
 * 出售门票，有3个售票员，一共有30张票
 *
 * @author AntonyCheng
 */

public class Demo02_2 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        // 定义3个售票员（创建三个线程）
        Thread sale01 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale01");
        Thread sale02 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale02");
        Thread sale03 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale03");
        // 让3个售票员开始售票（启动三个线程）
        sale01.start();
        sale02.start();
        sale03.start();
    }

}

/**
 * 编写门票资源类
 */
class Ticket {

    /**
     * 定义门票数量
     */
    private static int ticketNumber = 30;

    /**
     * 定义卖出数量
     */
    private static int saleNumber = 0;

    /**
     * 定义售票方法，这里使用synchronized修饰代码块
     */
    public void sale() {
        synchronized (this) {
            if (ticketNumber > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (++saleNumber) + "张票，还剩" + (--ticketNumber) + "张票");
            }
        }
    }

}
```

如果一个代码块被synchronized修饰了，当一个线程获取了对应的锁，并执行该代码块时，其他线程便只能一直等待，等待获取锁的线程释放锁，而这里获取锁的线程释放锁只会有两种情况：

1. 获取锁的线程执行完了该代码块，然后线程释放对锁的占有。
2. 线程执行发生异常，此时JVM会让线程自动释放锁。

那么如果这个获取锁的线程由于要等待IO或者其他原因（比如调用sleep方法）被阻塞了，但是又没有释放锁，其他线程便只能干巴巴地等待，试想一下，这多么影响程序执行效率。

因此就需要有一种机制可以不让等待的线程一直无期限地等待下去（比如只等待一定的时间或者能够响应中断），通过 Lock 接口就可以办到。

### 什么是Lock接口

Lock锁接口实现提供了比使用同步方法和语句可以获得的更广泛的锁操作。它们允许更灵活的结构，可能具有非常不同的属性，并且可能支持多个关联的条件对象。Lock提供了比synchronized更多的功能。

**Lock与的Synchronized区别：**

- Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问。
- Lock和synchronized有一点非常大的不同，采用synchronized不需要用户去手动释放锁，当synchronized方法或者synchronized代码块执行完之后，系统会自动让线程释放对锁的占用；而Lock则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。

#### Lock接口

```java
public interface Lock {
    
    /**
     * 获取锁，拿到锁就执行之后的代码，没拿到锁该线程就阻塞等待
     */
    void lock();

    /**
     * 获取锁，拿到锁就执行之后的代码，没拿到锁该线程就阻塞等待，但是等待的过程中被中断，就抛出异常
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * 尝试获取锁，拿到锁就返回true，没拿到锁就返回false
     */
    boolean tryLock();

    /**
     * 尝试在规定时间内获取锁，拿到锁就返回true，没拿到锁就返回false，如果等待锁的过程中被中断就抛出异常
     */
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    /**
     * 解锁
     */
    void unlock();

    /**
     * 返回Condition实例，该实例和锁相互绑定，可使用Condition实例进行await()等待和signal()唤醒操作
     */
    Condition newCondition();
}
```

该接口有[三个常用的实现类](./juc-base-demo/src/main/java/top/sharehome/demo02/Demo02_3.java)：`ReentrantLock` （可重入锁）、`ReentrantReadWriteLock.ReadLock` （读锁）、`ReentrantReadWriteLock.WriteLock` （写锁）。接下来以 ReentrantLock 类为例，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo02/Demo02_4.java)如下：

```java
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
```

对于`ReentrantReadWriteLock.ReadLock` （读锁）和`ReentrantReadWriteLock.WriteLock` （写锁）还有以下注意事项：

- 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
- 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。

#### Lock接口编程案例

使用Lock接口实现售票案例，即出售门票，有3个售票员，一共有30张票，[示例代码](./juc-base-demo/src/main/java/top/sharehome/demo02/Demo02_5.java)如下：

```java
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于Lock接口实现售票案例
 * 3个售票员，一共有30张票
 *
 * @author AntonyCheng
 */
public class Demo02_5 {

    public static void main(String[] args) {
        LockTicket ticket = new LockTicket();
        // 定义3个售票员（创建三个线程）
        Thread sale01 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale01");
        Thread sale02 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale02");
        Thread sale03 = new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "sale03");
        // 让3个售票员开始售票（启动三个线程）
        sale01.start();
        sale02.start();
        sale03.start();
    }

}

/**
 * 编写门票资源类
 */
class LockTicket {

    /**
     * 定义可重入锁
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * 定义门票数量
     */
    private static int ticketNumber = 30;

    /**
     * 定义卖出数量
     */
    private static int saleNumber = 0;

    /**
     * 定义售票方法，这里使用synchronized修饰代码块
     */
    public void sale() {
        LOCK.lock();
        try{
            if (ticketNumber > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (++saleNumber) + "张票，还剩" + (--ticketNumber) + "张票");
            }
        }finally {
            LOCK.unlock();
        }
    }

}
```

#### 小结（重点）

1. Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
2. synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
3. Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
4. 通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
5. Lock可以提高多个线程进行读操作的效率。