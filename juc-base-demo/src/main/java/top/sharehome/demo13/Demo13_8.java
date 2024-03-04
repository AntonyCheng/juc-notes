package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 合并两个有依赖关系的 CompletableFuture 的执行结果
 *
 * @author AntonyCheng
 */
public class Demo13_8 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        // 第一个CompletableFuture对一个数进行加10
        CompletableFuture<Integer> firstFuture = CompletableFuture.supplyAsync(() -> {
            return atomicInteger.addAndGet(10);
        });
        // 合并第二个CompletableFuture
        CompletableFuture<Integer> secondFuture = firstFuture.thenComposeAsync(res -> {
            // 第二个CompletableFuture对第一个CompletableFuture的结果平方
            return CompletableFuture.supplyAsync(() -> res * res);
        });
        try {
            System.out.println("第一个CompletableFuture计算结果为："+firstFuture.get());
            System.out.println("第二个CompletableFuture计算结果为："+secondFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
