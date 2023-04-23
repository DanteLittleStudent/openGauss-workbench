package com.gbase.admin.framework.manager;

import com.gbase.admin.common.utils.Threads;
import com.gbase.admin.common.utils.spring.SpringUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Asynchronous task manager
 *
 * @author xielibo
 */
public class AsyncManager {

    /**
     * Operation delay time. (millisecond)
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * Asynchronous operation task scheduling thread pool
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    private AsyncManager() {
    }

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    /**
     * Execute Task
     *
     * @param task task
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * shutdown
     */
    public void shutdown() {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
