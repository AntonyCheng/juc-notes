package top.sharehome.demo05;

/**
 * 模拟死锁示例
 *
 * @author AntonyCheng
 */

public class Demo05_4 {

    /**
     * 定义两把锁
     */
    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // A线程持有锁A
            synchronized (A) {
                System.out.println("A线程拿到锁A，接下来尝试获取锁B...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // A线程尝试获取锁B
                synchronized (B) {
                    System.out.println("A线程拿到锁B！");
                }
            }
        }, "A").start();
        new Thread(() -> {
            // B线程持有锁B
            synchronized (B) {
                System.out.println("B线程拿到锁B，接下来尝试获取锁A...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // B线程尝试获取锁A
                synchronized (A) {
                    System.out.println("B线程拿到锁A！");
                }
            }
        }).start();
    }

}
