package top.sharehome.demo06;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Callable + FutureTask 示例代码
 *
 * @author AntonyCheng
 */

public class Demo06_2 {

    public static void main(String[] args) throws Exception {
        // 创建FutureTask任务类
        FutureTask<Integer> futureTask = new FutureTask<>(new Demo06_2Callable());
        // 启动另一条线程执行任务
        new Thread(futureTask).start();
        // 轮询任务是否结束，轮询周期为0.5s
        while (!futureTask.isDone()){
            Thread.sleep(500);
            System.out.println("futureTask正在执行...");
        }
        // 监控到任务已经结束，即获取最终返回值
        System.out.println("futureTask已结束，状态码为：" + futureTask.get());
    }

}

/**
 * 实现Callable接口，泛型就表示返回值的类型
 */
class Demo06_2Callable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "：实现Callable接口创建线程...");
        // 假设该计算耗时3s
        Thread.sleep(3000);
        // 一般正常返回响应状态码为200
        return 200;
    }

}

