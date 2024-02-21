package top.sharehome.demo02;

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