package com.hsd.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutorUtil.class);

    private static final int AWAIT_TERMINATION_SECONDS = 3;

    public static ThreadPoolTaskExecutor newThreadPoolTaskExecutor(int corePoolSize,
                                                                   int maximumPoolSize,
                                                                   int queueCapacity,
                                                                   long keepAliveTime,
                                                                   TimeUnit unit,
                                                                   String threadNamePrefix,
                                                                   RejectedExecutionHandler handler) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds((int) unit.toSeconds(keepAliveTime));
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(handler);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        return executor;
    }

    public static ThreadPoolTaskScheduler newThreadPoolTaskScheduler(int poolSize, String threadNamePrefix) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix(threadNamePrefix);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        scheduler.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        return scheduler;
    }

    public static void registerShutDownHook(ThreadPoolExecutor executor, String name) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("正在关闭线程池{} . . .", name);
            executor.shutdown();
            try {
                LOGGER.info("等待线程池处理，{}秒后强制关闭线程池{}", AWAIT_TERMINATION_SECONDS, name);
                if (!executor.awaitTermination(AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            LOGGER.info("关闭线程池{}成功", name);
        }));
    }

}
