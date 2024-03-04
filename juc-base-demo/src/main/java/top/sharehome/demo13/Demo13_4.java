package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 先用一个线程将一个数加10，然后再对其取平方，最后得到结果
 *
 * @author AntonyCheng
 */
public class Demo13_4 {

    public static void main(String[] args) {
        AtomicInteger initNum = new AtomicInteger(10);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
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
            int i = num.accumulateAndGet(num.get(), (num1, num2) -> num1 * num2);
            System.out.println(Thread.currentThread().getName() + "线程：平方操作完成！");
            return i;
        });
        try {
            System.out.println(Thread.currentThread().getName() + "线程：等待异步程序执行...");
            Integer res = future.get();
            System.out.println(Thread.currentThread().getName() + "线程：异步程序执行结果为" + res);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
