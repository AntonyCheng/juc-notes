package top.sharehome.demo06;

/**
 * 使用Thread和Runnable创建线程示例代码
 *
 * @author AntonyCheng
 */

public class Demo06_1 {

    public static void main(String[] args) {
        // 1、通过Thread类匿名创建线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "：通过Thread类创建线程...");
        }).start();
        // 2、实现Runnable接口创建线程
        Demo06_1Runnable runnable = new Demo06_1Runnable();
        new Thread(runnable).start();
    }

}

/**
 * 实现Runnable接口
 */
class Demo06_1Runnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "：实现Runnable接口创建线程...");
    }
}

