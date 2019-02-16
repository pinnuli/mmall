package com.mmall.concurrency.example.commonUnsafe;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用堆栈封闭实现线程安全，
 * 将SimpleDateFormat设置为局部变量
 * @author: pinnuli
 * @date: 2019-02-16
 */
@Slf4j
public class DateFormatExample2 {


    public static int clientTotal = 5000;

    public static int threadTotal = 200;

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
            });
        }
        countDownLatch.await();
        executorService.shutdown();

    }

    public static void update(int i) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            log.info("{}, {}", i, simpleDateFormat.parse("2019-02-16"));
        } catch (Exception e) {
            log.error("parse exception", e);
        }
    }
}
