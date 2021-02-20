package extra.lesson1;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

/**
 * 通过 parallel 方法，一键把 Stream 转换为并行操作提交到线程池处理
 * 当前机器是6核12线程，所以1秒内输出12个
 */
public class ParallelStream {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).parallel().forEach(i -> {
            System.out.println(LocalDateTime.now() + " : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        });
    }
}
