package com.gbase.admin.framework.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Make sure that the background thread can be closed when the application exits
 *
 * @author xielibo
 */
@Component
@Slf4j
public class ShutdownManager {

    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * Stop executing tasks asynchronously
     */
    private void shutdownAsyncManager() {
        try {
            log.info("====Close background task thread pool====");
            AsyncManager.me().shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
