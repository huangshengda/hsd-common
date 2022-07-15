package com.hsd.thread.syncbatch;

import com.hsd.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步批量写入器
 */
@Slf4j
public abstract class AbstractAsyncBatchWriter<T> {

    private static final int MAX_QUEUE_SIZE = 10000;

    /**
     * 计数器
     */
    private final AtomicInteger counter = new AtomicInteger(0);
    /**
     * 队列
     */
    private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
    private final Executor executor;

    public AbstractAsyncBatchWriter() {
        this.executor = new ThreadPoolExecutor(2, 2, 10L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new NamedThreadFactory("batch-pack-"), new ThreadPoolExecutor.AbortPolicy());
        start();
    }

    public void write(T t) {
        if (counter.get() >= MAX_QUEUE_SIZE) {
            throw new RuntimeException(String.format("%s queue is full", getClass().getName()));
        }
        queue.add(t);
        counter.incrementAndGet();
    }

    public String getWriterName() {
        return getClass().getSimpleName();
    }

    /**
     * 获取执行器
     *
     * @return 执行器
     */
    protected abstract ThreadPoolTaskExecutor getExecutor();

    /**
     * 批量写入
     *
     * @param batchList 批次
     */
    protected abstract void batchWrite(List<T> batchList);

    /**
     * 是否运行中
     *
     * @return boolean
     */
    protected abstract boolean isRunning();

    /**
     * 获取批次大小
     *
     * @return 批次大小
     */
    protected abstract int getBatchSize();

    /**
     * flush间隔时间
     *
     * @return flush间隔时间
     */
    protected abstract long getFlushInterval();

    public AtomicInteger getCounter() {
        return counter;
    }

    protected void start() {
        executor.execute(new BatchPackThread());
    }

    private class BatchPackThread implements Runnable {

        /**
         * 最小flush间隔时间，毫秒
         */
        private static final int MIN_FLUSH_INTERVAL = 50;
        /**
         * 最大flush间隔时间，毫秒
         */
        private static final int MAX_FLUSH_INTERVAL = 200;
        private volatile long lastPackTimeMillis = System.currentTimeMillis();

        @Override
        public void run() {
            log.info("{} start ...", getWriterName());

            while (isRunning()) {
                if (counter.get() < getBatchSize() && !isForceFlush()) {
                    try {
                        Thread.sleep(MIN_FLUSH_INTERVAL);
                    } catch (InterruptedException e) {
                        log.error(String.format("%s sleep error", getWriterName()), e);
                    }
                    continue;
                }
                List<T> batchList = pack();
                if (batchList.size() == 0) {
                    writePack(batchList);
                }
            }

            log.info("{} flush ...", getWriterName());

            for (; ; ) {
                List<T> batchList = pack();
                if (batchList.size() == 0) {
                    break;
                }
                writePack(batchList);
            }

            //executor.shutdown();
            log.info("{} closed", getWriterName());
        }

        private boolean isForceFlush() {
            long flushInterval = Math.min(Math.max(getFlushInterval(), MIN_FLUSH_INTERVAL), MAX_FLUSH_INTERVAL);
            return System.currentTimeMillis() - lastPackTimeMillis > flushInterval;
        }

        private List<T> pack() {
            int size = counter.get();
            if (size <= 0) {
                lastPackTimeMillis = System.currentTimeMillis();
                return Collections.emptyList();
            }

            int batchSize = getBatchSize();
            List<T> batchList = new ArrayList<>(Math.min(batchSize, size + 5));
            while (batchList.size() < batchSize) {
                T t = queue.poll();
                if (t == null) {
                    break;
                }
                batchList.add(t);
            }

            counter.addAndGet(-batchList.size());
            lastPackTimeMillis = System.currentTimeMillis();
            return batchList;
        }

        private void writePack(List<T> batchList) {
            try {
                log.info(String.format("%s writePack batchSize=%d", getWriterName(), batchList.size()));
                getExecutor().submit(() -> batchWrite(batchList));
            } catch (Exception e) {
                log.error(String.format("%s writePack error", getWriterName()), e);
            }
        }

    }

}
