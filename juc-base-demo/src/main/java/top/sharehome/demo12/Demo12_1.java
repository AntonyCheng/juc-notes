package top.sharehome.demo12;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架示例
 *
 * @author AntonyCheng
 */
public class Demo12_1 {

    public static void main(String[] args) {
        Demo12_1ForkJoin demo121ForkJoin = new Demo12_1ForkJoin(1, 100);
        // 创建ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            // 提交计算任务
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(demo121ForkJoin);
            // 获取计算结果
            Integer res = forkJoinTask.get();
            System.out.println("1~100相加得：" + res);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 最后关闭ForkJoinPool
            forkJoinPool.shutdown();
        }
    }

}

/**
 * Fork/Join示例类
 */
class Demo12_1ForkJoin extends RecursiveTask<Integer> {

    /**
     * 计算起始值
     */
    private final int start;

    /**
     * 计算中止值
     */
    private final int end;

    /**
     * 计算总和
     */
    private int sum;

    /**
     * 有参构造器
     */
    public Demo12_1ForkJoin(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - start) < 10) {
            // 定义每部分相加结果
            int tempRes = 0;
            // 如果相差小于10，那么就连续累加
            for (int i = start; i <= end; i++) {
                sum += i;
                tempRes += i;
            }
            System.out.println("任务：" + start + "~" + end + "累加完毕得" + sum);
        } else {
            // 如果相加大于等于10，那么就二分
            int mid = (start + end) / 2;
            // 构造左边拆分
            Demo12_1ForkJoin left = new Demo12_1ForkJoin(start, mid);
            // 构造右边拆分
            Demo12_1ForkJoin right = new Demo12_1ForkJoin(mid + 1, end);
            // 调用拆分
            left.fork();
            right.fork();
            // 合并结果
            sum = left.join() + right.join();
        }
        return sum;
    }
}