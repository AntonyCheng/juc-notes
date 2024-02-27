package top.sharehome.demo07;

import java.time.LocalDateTime;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 辅助类：信号灯Semaphore
 *
 * @author AntonyCheng
 */
public class Demo07_3 {

    /**
     * 抢车位，6部汽车，3个停车位
     */
    public static void main(String[] args) {
        // 定义信号灯，模拟出3个停车位
        Semaphore semaphore = new Semaphore(3);

        // 模拟6辆汽车去抢车位
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(LocalDateTime.now()+ " ==> " +Thread.currentThread().getName()+"：抢到车位，停两秒再走...");
                    Thread.sleep(2000);
                    System.out.println(LocalDateTime.now()+ " ==> " +Thread.currentThread().getName()+"：走了走了!");
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },"第"+(i+1)+"辆车").start();
        }
    }

}
