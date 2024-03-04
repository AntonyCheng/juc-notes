package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 运行一个有返回值的异步任务
 *
 * @author AntonyCheng
 */
public class Demo13_3 {

    public static void main(String[] args) {
        // 运行一个需要返回值的异步方法
        CompletableFuture<Integer> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程：正在投递日志消息...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：投递消息成功！");
            return 200;
        });
        // 结束消息投递
        try {
            System.out.println(Thread.currentThread().getName() + "线程：等待异步任务调用完成...");
            Integer res = voidCompletableFuture.get();
            if (res == 200) {
                System.out.println(Thread.currentThread().getName() + "线程：异步线程结束，返回值为" + res + "，本线程也结束！");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
