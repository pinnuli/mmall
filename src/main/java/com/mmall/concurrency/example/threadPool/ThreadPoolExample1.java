package com.mmall.concurrency.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newCachedThreadPool,创建可缓存的线程池，
 * 如果线程池长度超过实际需要，可以灵活回收空闲线程，
 * 如果没有空闲线程，就创建线程
 */
@Slf4j
public class ThreadPoolExample1 {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("task:{}", index);
                }
            });
        }
        executorService.shutdown();
    }
}
