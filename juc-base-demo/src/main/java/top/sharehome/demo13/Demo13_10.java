package top.sharehome.demo13;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一系列独立的 CompletableFuture 任务，等其所有的任务执行完后做一些事情
 *
 * @author AntonyCheng
 */
public class Demo13_10 {

    public static void main(String[] args) {
        // 定义初始化值
        AtomicInteger atomicInteger = new AtomicInteger(10);
        // 定义结果值
        AtomicInteger result = new AtomicInteger(0);
        // 第一个CompletableFuture对一个数进行加10
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return atomicInteger.addAndGet(10);
        });
        // 第二个CompletableFuture对同一个数进行加20
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return atomicInteger.addAndGet(20);
        });
        // 第三个CompletableFuture对同一个数再进行加30
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return atomicInteger.addAndGet(30);
        });
        try {
            // 将三个CompletableFuture的结果相加
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(future1, future2, future3);
            Integer result1 = future1.get();
            result.addAndGet(result1);
            System.out.println(LocalDateTime.now() + " ==> " + result1);
            Integer result2 = future2.get();
            result.addAndGet(result2);
            System.out.println(LocalDateTime.now() + " ==> " + result2);
            Integer result3 = future3.get();
            result.addAndGet(result3);
            System.out.println(LocalDateTime.now() + " ==> " + result3);
            // 没有合并完之前，不会执行voidCompletableFuture.get()之后的程序
            voidCompletableFuture.get();
            System.out.println("相加结果为：" + result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
