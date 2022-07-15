package com.hsd.thread.schedule;

import com.hsd.thread.NamedThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-07-13 09:26
 */
public class ScheduleDemo {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("sentinel-metrics-record-task", true));

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start->" + System.currentTimeMillis());
        SCHEDULER.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + ","+ System.currentTimeMillis());
            System.out.println("1");
        }, 10, TimeUnit.SECONDS);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
