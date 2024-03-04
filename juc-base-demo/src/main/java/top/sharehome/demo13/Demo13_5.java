package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 先用一个线程将一个数加10，然后再对其取平方，最后在异步线程中继续处理最后的数据
 *
 * @author AntonyCheng
 */
public class Demo13_5 {

    public static void main(String[] args) {
        AtomicInteger initNum = new AtomicInteger(10);
        CompletableFuture.supplyAsync(() -> {
            // 模拟程序执行延迟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：正在执行加10操作...");
            initNum.addAndGet(10);
            System.out.println(Thread.currentThread().getName() + "线程：加10操作完成！");
            return initNum;
        }).thenApplyAsync(num -> {
            // 模拟程序执行延迟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：正在执行平方操作...");
            int i = num.get();
            System.out.println(Thread.currentThread().getName() + "线程：平方操作完成！");
            return i * i;
        }).thenAcceptAsync(res -> {
            System.out.println(Thread.currentThread().getName() + "线程：已经计算出最后结果为" + res);
        });
        System.out.println(Thread.currentThread().getName() + "线程：等待异步程序执行...");
        try {
            // 由于以上的xxxAsync操作均在守护线程中进行，需要阻塞主线程
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("整个程序执行完成！");
    }

}
