package top.sharehome.demo03;

/**
 * 要求在多线程环境下，首先让A线程打印1次”A“，然后让B线程打印2次”B“，再让C线程打印3次”C“，最后让D线程打印4次”D“，上述操作要求循环3次
 *
 * @author AntonyCheng
 */
public class Demo03_5 {

    public static void main(String[] args) {
        Demo03_5Customized customized = new Demo03_5Customized();
        customized.test();
    }

}

/**
 * 定制化实现类
 */
class Demo03_5Customized {

    /**
     * 定义标志符号，用1、2、3、4代指A、B、C、D线程，初始默认为A线程
     */
    private static int mark = 1;

    /**
     * 编写A、B、C、D四个线程的打印方法
     */
    private synchronized void printA() {
        try {
            // 如果标志符号为1，就是A线程，否则让A线程循环等待
            while (mark != 1) {
                wait();
            }
            System.out.print(Thread.currentThread().getName() + ": ");
            for (int i = 0; i < 1; i++) {
                System.out.print("A");
            }
            System.out.println();
            // 修改为B线程的标志符号
            mark = 2;
            // B线程执行完之后唤醒所有线程
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void printB() {
        try {
            // 如果标志符号为2，就是B线程，否则让B线程循环等待
            while (mark != 2) {
                wait();
            }
            System.out.print(Thread.currentThread().getName() + ": ");
            for (int i = 0; i < 2; i++) {
                System.out.print("B");
            }
            System.out.println();
            // 修改为C线程的标志符号
            mark = 3;
            // B线程执行完之后唤醒所有线程
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void printC() {
        try {
            // 如果标志符号为3，就是C线程，否则让C线程循环等待
            while (mark != 3) {
                wait();
            }
            System.out.print(Thread.currentThread().getName() + ": ");
            for (int i = 0; i < 3; i++) {
                System.out.print("C");
            }
            System.out.println();
            // 修改为D线程的标志符号
            mark = 4;
            // C线程执行完之后唤醒所有线程
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void printD() {
        try {
            // 如果标志符号为4，就是D线程，否则让D线程循环等待
            while (mark != 4) {
                wait();
            }
            System.out.print(Thread.currentThread().getName() + ": ");
            for (int i = 0; i < 4; i++) {
                System.out.print("D");
            }
            System.out.println();
            // 修改为A线程的标志符号
            mark = 1;
            // D线程执行完之后唤醒所有线程
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对外测试类
     */
    public void test() {
        for (int i = 0; i < 3; i++) {
            new Thread(this::printA, "A").start();
            new Thread(this::printB, "B").start();
            new Thread(this::printC, "C").start();
            new Thread(this::printD, "D").start();
        }
    }

}