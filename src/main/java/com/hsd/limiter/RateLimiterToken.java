package com.hsd.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 令牌桶
 *
 * @author huangshengda
 * @date 2022-07-22 16:09
 */
public class RateLimiterToken {

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(time + ":" + rateLimiter.tryAcquire());
            Thread.sleep(250);
        }
    }

}
