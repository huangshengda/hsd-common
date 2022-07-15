package com.hsd.thread.syncbatch;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-07-15 15:57
 */
public class DemoWriter extends AbstractAsyncBatchWriter<Object> {

    @Override
    protected ThreadPoolTaskExecutor getExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    protected void batchWrite(List<Object> batchList) {
        System.out.println("do writer");
    }

    @Override
    protected boolean isRunning() {
        return true;
    }

    @Override
    protected int getBatchSize() {
        return 10;
    }

    @Override
    protected long getFlushInterval() {
        return 10;
    }
}
