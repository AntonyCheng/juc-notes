package top.sharehome.demo09;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列示例代码
 *
 * @author AntonyCheng
 */
public class Demo09_1 {

    public static void main(String[] args) {
        Demo09_1BlockingQueue blockingQueue = new Demo09_1BlockingQueue();
        System.out.println("===============第一组方法：add(obj)、remove()以及element()");
        blockingQueue.inAndOut1();
        System.out.println("\n===============第二组方法：offer(obj)、poll()以及peek()");
        blockingQueue.inAndOut2();
        System.out.println("\n===============第三组方法：put(obj)、take()");
        blockingQueue.inAndOut3();
        System.out.println("\n===============第四组方法：offer(e,time,unit)、poll(time,unit)");
        blockingQueue.inAndOut4();
    }

}

/**
 * 阻塞队列类
 */
class Demo09_1BlockingQueue {

    /**
     * 创建一个容量为3的阻塞队列
     */
    private final ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    /**
     * 第一组方法：add(obj)、remove()以及element()
     */
    public void inAndOut1() {
        // 向阻塞队列中使用尾插法插入三个元素
        System.out.println("插入a" + (blockingQueue.add("a") ? "成功" : "失败"));
        System.out.println("插入b" + (blockingQueue.add("b") ? "成功" : "失败"));
        System.out.println("插入c" + (blockingQueue.add("c") ? "成功" : "失败"));
        // 查看队首元素
        System.out.println("此时队首元素为" + blockingQueue.element());
        // 如果再向队列中插入数据，由于满队，add()方法会抛出异常
        try {
            blockingQueue.add("d");
        } catch (IllegalStateException e) {
            System.out.println("抓住异常：" + e.getClass().getName() + "，插入元素失败");
        }
        // 开始移除队列中的元素
        System.out.println("接下来移除队列中的元素");
        System.out.println("成功移除" + blockingQueue.remove());
        System.out.println("成功移除" + blockingQueue.remove());
        System.out.println("成功移除" + blockingQueue.remove());
        // 查看队首元素，但由于空队，element()方法会抛出异常
        try {
            System.out.println("此时队首元素为" + blockingQueue.element());
        } catch (NoSuchElementException e) {
            System.out.println("抓住异常：" + e.getClass().getName() + ",查看队首元素失败");
        }
        // 如果再让队列移除数据，由于空队，remove()方法会抛出异常
        try {
            blockingQueue.remove();
        } catch (NoSuchElementException e) {
            System.out.println("抓住异常：" + e.getClass().getName() + ",移除元素失败");
        }
    }

    /**
     * 第二组方法：offer(obj)、poll()以及peek()
     */
    public void inAndOut2() {
        // 向阻塞队列中使用尾插法插入三个元素
        System.out.println("插入a" + (blockingQueue.offer("a") ? "成功" : "失败"));
        System.out.println("插入b" + (blockingQueue.offer("b") ? "成功" : "失败"));
        System.out.println("插入c" + (blockingQueue.offer("c") ? "成功" : "失败"));
        // 查看队首元素
        System.out.println("此时队首元素为" + blockingQueue.peek());
        // 如果再向队列中插入数据，由于满队，offer()方法会返回false
        System.out.println("插入d" + (blockingQueue.offer("d") ? "成功" : "失败"));
        // 开始移除队列中的元素
        System.out.println("接下来移除队列中的元素");
        System.out.println("成功移除" + blockingQueue.poll());
        System.out.println("成功移除" + blockingQueue.poll());
        System.out.println("成功移除" + blockingQueue.poll());
        // 查看队首元素，但由于空队，peek()方法会返回null
        System.out.println("此时队首元素为" + blockingQueue.peek());
        // 如果再让队列移除数据，由于空队，poll()方法会返回null
        System.out.println("移除的元素为" + blockingQueue.poll());
    }

    /**
     * 第三组方法：put(obj)、take()
     */
    public void inAndOut3() {
        try {
            // 向阻塞队列中使用尾插法插入三个元素
            blockingQueue.put("a");
            System.out.println("插入a成功");
            blockingQueue.put("b");
            System.out.println("插入b成功");
            blockingQueue.put("c");
            System.out.println("插入c成功");
            // 查看队首元素
            System.out.println("此时队首元素为" + blockingQueue.peek());
            // 此时阻塞队列已经满队，如果再向队列中插入元素，就会造成阻塞，所以在插入之前用另外一条线程对其模拟移除元素的操作
            new Thread(() -> {
                try {
                    // 先让线程睡1s，模拟正在处理其他请求
                    Thread.sleep(1000);
                    // 然后从队列中移除元素，用任何移除方法均可
                    System.out.println("子线程移除了队首元素：" + blockingQueue.remove());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            System.out.println("插入d时发生了阻塞，等待中...");
            blockingQueue.put("d");
            // 为了更好的演示和避免因println方法耗时过长打印顺序混乱的问题，线程小睡0.1s
            Thread.sleep(100);
            System.out.println("插入d成功");
            // 开始移除队列中的元素
            System.out.println("接下来移除队列中的元素");
            System.out.println("成功移除" + blockingQueue.take());
            System.out.println("成功移除" + blockingQueue.take());
            System.out.println("成功移除" + blockingQueue.take());
            // 此时阻塞队列已经空队，如果再从队列中移除元素，就会造成阻塞，所以在移除之前用另外一条线程对其模拟插入元素的操作
            new Thread(() -> {
                try {
                    // 先让线程睡1s，模拟正在处理其他请求
                    Thread.sleep(1000);
                    // 然后向队列中插入元素，用任何插入方法均可
                    blockingQueue.offer("e");
                    System.out.println("子线程尾插入元素：e");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            System.out.println("移除时发生了阻塞，等待中...");
            String take = blockingQueue.take();
            // 为了更好的演示和避免因println方法耗时过长打印顺序混乱的问题，线程小睡0.1s
            Thread.sleep(100);
            System.out.println("移除" + take + "成功");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 第四组方法：offer(e,time,unit)、poll(time,unit)
     */
    public void inAndOut4() {
        try {
            // 向阻塞队列中使用尾插法插入三个元素
            System.out.println("插入a" + (blockingQueue.offer("a") ? "成功" : "失败"));
            System.out.println("插入b" + (blockingQueue.offer("b") ? "成功" : "失败"));
            System.out.println("插入c" + (blockingQueue.offer("c") ? "成功" : "失败"));
            // 查看队首元素
            System.out.println("此时队首元素为" + blockingQueue.peek());
            // 再定时插入一个
            System.out.println("长时间满队，插入d" + (blockingQueue.offer("d", 1, TimeUnit.SECONDS) ? "成功" : "失败"));
            // 开始移除队列中的元素
            System.out.println("接下来移除队列中的元素");
            System.out.println("成功移除" + blockingQueue.poll());
            System.out.println("成功移除" + blockingQueue.poll());
            System.out.println("成功移除" + blockingQueue.poll());
            // 再定时移除一个
            System.out.println("长时间空队，移除"+blockingQueue.poll(1,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
