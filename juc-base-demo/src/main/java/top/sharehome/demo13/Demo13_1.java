package top.sharehome.demo13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 主线程里面创建一个CompletableFuture，然后主线程调用get()方法会阻塞，最后我们在一个子线程中使其终止
 *
 * @author AntonyCheng
 */

public class Demo13_1 {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行代码...");
            // 异步返回200
            future.complete(200);
            System.out.println(Thread.currentThread().getName() + "结束执行代码！");
        }, "子线程").start();
        try {
            System.out.println(Thread.currentThread().getName() + "线程获取异步返回结果为：" + future.get());
            System.out.println(Thread.currentThread().getName() + "线程获取到异步结果，阻塞结束！");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
