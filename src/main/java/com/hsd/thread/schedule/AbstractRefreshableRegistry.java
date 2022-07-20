package com.hsd.thread.schedule;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 可刷新的注册中心
 *
 * @author huangshengda
 */
@Slf4j
public abstract class AbstractRefreshableRegistry<T> extends AbstractApplicationInitializationListener {

    @Resource
    private ThreadPoolTaskScheduler commonScheduler;

    private final int refreshIntervalSecond;

    private volatile long lastUpdateTime = 0;

    /**
     * 默认刷新间隔时间5s
     */
    private static final int DEFAULT_REFRESH_INTERVAL_SECOND = 5;

    public AbstractRefreshableRegistry() {
        this(DEFAULT_REFRESH_INTERVAL_SECOND);
    }

    public AbstractRefreshableRegistry(int refreshIntervalSecond) {
        this.refreshIntervalSecond = refreshIntervalSecond;
    }

    @Override
    protected void init() throws Exception {
        refresh();
        commonScheduler.scheduleWithFixedDelay(this::refresh, refreshIntervalSecond * 1000);
    }

    private void refresh() {
        List<T> list = fetchLatestUpdateData(new Date(lastUpdateTime));
        if (list == null) {
            return;
        }

        for (T t : list) {
            log.info("{} refreshData {}", getClass().getSimpleName(), JSON.toJSONString(t));
            try {
                refreshData(t);
            } catch (Exception e) {
                log.error("", e);
            }
            Date updateTime = getDataUpdateTime(t);
            if (lastUpdateTime < updateTime.getTime()) {
                lastUpdateTime = updateTime.getTime();
            }
        }
    }

    /**
     * 获取最新更新的数据
     *
     * @param lastUpdateTime 最近更新时间
     * @return 最新更新的数据
     */
    protected abstract List<T> fetchLatestUpdateData(Date lastUpdateTime);

    /**
     * 获取数据的更新时间
     *
     * @param t 数据
     * @return 更新时间
     */
    protected abstract Date getDataUpdateTime(T t);

    /**
     * 刷新数据
     *
     * @param t 数据
     * @throws Exception 异常
     */
    protected abstract void refreshData(T t) throws Exception;

}
