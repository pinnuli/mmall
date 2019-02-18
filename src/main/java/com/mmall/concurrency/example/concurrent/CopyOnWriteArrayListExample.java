package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * CopyOnWriteArrayList,线程安全，当对CopyOnWriteArrayList进行写操作时，
 * 现将原来的数组拷贝一份，然后在新的数组里面进行写操作，写完之后再将原来的数组指向新的数组，
 * 读操作时不需要加锁，写操作时需要加锁；
 * CopyOnWriteArrayList有如下缺点：
 * 1.拷贝的时候会消耗内存，当元素个数太多时，可能会导致Minor GC或者Full GC
 * 2.没办法满足实时读的要求，复制和新增元素都需要时间，他只能满足最终的一致性，适合于读多写少的场景
 *
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int i) {
        list.add(i);
    }
}
