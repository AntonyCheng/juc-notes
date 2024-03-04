package top.sharehome.demo13;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一系列独立的 CompletableFuture 任务，只要有任务完成就结束其他任务
 *
 * @author AntonyCheng
 */
public class Demo13_11 {

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
            // 等待一个CompletableFuture任务完成就返回
            CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(future1, future2, future3);
            result.addAndGet((Integer) objectCompletableFuture.get());
            System.out.println("第一个完成的任务结果为：" + result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
