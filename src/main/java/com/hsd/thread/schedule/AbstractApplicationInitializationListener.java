package com.hsd.thread.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author huangshengda
 * @date 2020/7/29
 */
@Slf4j
public abstract class AbstractApplicationInitializationListener implements ApplicationListener<ContextRefreshedEvent> {

    private volatile boolean initialized = false;

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    protected abstract void init() throws Exception;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized) {
            return;
        }
        initialized = true;

        try {
            init();
        } catch (Exception e) {
            log.error(String.format("%s init error exception=%s", getClass().getSimpleName(), e.getMessage()), e);
            throw new RuntimeException(e);
        }
    }

}
