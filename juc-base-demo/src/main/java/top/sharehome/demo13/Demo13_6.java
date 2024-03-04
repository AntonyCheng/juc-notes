package top.sharehome.demo13;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异常处理方案
 *
 * @author AntonyCheng
 */
public class Demo13_6 {

    /**
     * 仅监听异常
     */
    private static void method01() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟程序执行延迟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：正在执行异步操作...");
            int randomNum = new Random().nextInt(100);
            if (randomNum % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + "线程：异步操作完成！");
                return randomNum;
            } else {
                throw new RuntimeException("Random number is invalid");
            }
        }).exceptionally(ex -> {
            System.out.println(Thread.currentThread().getName() + "线程：抓住异常——" + ex.getMessage());
            return 500;
        });
        System.out.println(Thread.currentThread().getName() + "线程：等待异步程序执行...");
        try {
            Integer futureRes = future.get();
            if (futureRes == 500) {
                System.out.println(Thread.currentThread().getName() + "线程：异步程序出现错误！");
            } else {
                System.out.println(Thread.currentThread().getName() + "线程：异步程序执行成功，返回结果为：" + futureRes);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 监听异常和结果
     */
    private static void method02() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟程序执行延迟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：正在执行异步操作...");
            int randomNum = new Random().nextInt(100);
            if (randomNum % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + "线程：异步操作完成！");
                return randomNum;
            } else {
                throw new RuntimeException("Random number is invalid");
            }
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println(Thread.currentThread().getName() + "线程：抓住异常——" + ex.getMessage());
                return 500;
            } else {
                return res;
            }
        });
        System.out.println(Thread.currentThread().getName() + "线程：等待异步程序执行...");
        try {
            Integer futureRes = future.get();
            if (futureRes == 500) {
                System.out.println(Thread.currentThread().getName() + "线程：异步程序出现错误！");
            } else {
                System.out.println(Thread.currentThread().getName() + "线程：异步程序执行成功，返回结果为：" + futureRes);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        method01();
        System.out.println();
        method02();
    }

}

