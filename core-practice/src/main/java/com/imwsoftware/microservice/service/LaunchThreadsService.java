package com.imwsoftware.microservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class LaunchThreadsService {
    public void launchThreads() {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info("Thread {} is running", Thread.currentThread().getName());
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Error waiting for threads to finish", e);
        }
    }

    public void launchExecutorThreads() {
        try {
            ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    log.info("Executor thread {} is running", Thread.currentThread().getName());
                });
            }
            executor.shutdown();
        } catch (Exception e) {
            log.error("Error launching executor threads", e);
        }
    }
}
