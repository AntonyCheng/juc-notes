package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 合并两个没有依赖关系的 CompletableFuture 的执行结果
 *
 * @author AntonyCheng
 */
public class Demo13_9 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        // 第一个CompletableFuture对一个数进行加10
        CompletableFuture<Integer> firstFuture = CompletableFuture.supplyAsync(() -> atomicInteger.addAndGet(10));
        // 第二个CompletableFuture对同一个数进行乘10
        CompletableFuture<Integer> secondFuture = CompletableFuture.supplyAsync(() -> atomicInteger.accumulateAndGet(10, (left, right) -> left * right));
        // 将两个CompletableFuture的结果相加
        CompletableFuture<Integer> resFuture = firstFuture.thenCombine(secondFuture, Integer::sum);
        try {
            System.out.println("两个CompletableFuture合并相加结果为：" + resFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
