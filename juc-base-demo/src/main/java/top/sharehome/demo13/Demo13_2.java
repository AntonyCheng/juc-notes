package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 运行一个没有返回值的异步任务
 *
 * @author AntonyCheng
 */
public class Demo13_2 {

    public static void main(String[] args) {
        // 使用自定义线程执行器运行一个不需要返回值的异步方法
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程：正在投递日志消息...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "线程：投递消息成功！");
        });
        // 结束消息投递
        try {
            System.out.println(Thread.currentThread().getName() + "线程：等待异步任务调用完成...");
            voidCompletableFuture.get();
            System.out.println(Thread.currentThread().getName() + "线程：异步线程结束，本线程也结束！");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
