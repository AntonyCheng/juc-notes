package top.sharehome.demo06;

import java.util.concurrent.FutureTask;

/**
 * Callable + FutureTask 匿名形式示例代码
 *
 * @author AntonyCheng
 */

public class Demo06_3 {

    public static void main(String[] args) throws Exception {
        // 定义FutureTask对象
        FutureTask<Integer> futureTask = null;
        // 启动另一条线程执行任务
        new Thread(futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "：实现Callable接口创建线程...");
            // 假设该计算耗时3s
            Thread.sleep(3000);
            // 一般正常返回响应状态码为200
            return 200;
        })).start();
        // 轮询任务是否结束，轮询周期为0.5s，如果没有while轮询任务，那么到get()方法时就会阻塞
        while (!futureTask.isDone()) {
            Thread.sleep(500);
            System.out.println("futureTask正在执行...");
        }
        // 监控到任务已经结束，即获取最终返回值
        System.out.println("futureTask已结束，状态码为：" + futureTask.get());
    }

}
