package com.msb.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description  CountDownLatchTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 14:42  2022/11/2
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.MILLISECONDS
                , new LinkedBlockingQueue<>());
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            CountDownLatch latch = new CountDownLatch(3);
            for (int j = 0; j < 3; j++) {
                final int flag = j;
                executor.execute(() -> {
                    final int cur = counter.incrementAndGet();
                    try {
                        System.out.printf("[%d%d]step: 1\n", cur, flag);
                        Thread.sleep(3_000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.printf("[%d%d]step: 2\n",cur, flag);
                        latch.countDown();
                    }
                });
            }
            latch.await(2, TimeUnit.SECONDS);
        }

    }
}
