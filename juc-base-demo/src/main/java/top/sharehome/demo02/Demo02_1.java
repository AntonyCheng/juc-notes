package top.sharehome.demo02;

/**
 * Synchronized关键字使用
 *
 * @author AntonyCheng
 */

public class Demo02_1 {

    public static void main(String[] args) {
        Sync sync = new Sync();
        // 同步代码块
        sync.syncCodeBlock();
        // 同步方法
        sync.syncMethod();
        // 同步静态方法
        Sync.syncStaticMethod();
    }

}

/**
 * 同步演示类
 */
class Sync {
    /**
     * 修饰代码块
     */
    public void syncCodeBlock() {
        synchronized (this) {
            System.out.println("同步代码块...");
        }
    }

    /**
     * 修饰方法
     */
    public synchronized void syncMethod() {
        System.out.println("同步方法...");
    }

    /**
     * 修饰静态方法
     */
    public static synchronized void syncStaticMethod() {
        System.out.println("同步静态方法...");
    }

}