package com.mmall.concurrency.example.commonUnsafe;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用joda实现线程安全，
 * @author: pinnuli
 * @date: 2019-02-16
 */
@Slf4j
public class DateFormatExample3 {


    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

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
            log.info("{}, {}", i, DateTime.parse("2018-02-08", dateTimeFormatter).toDate());
        } catch (Exception e) {
            log.error("parse exception", e);
        }
    }
}
