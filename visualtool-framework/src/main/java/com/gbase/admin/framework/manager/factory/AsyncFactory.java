package com.gbase.admin.framework.manager.factory;

import com.gbase.admin.common.utils.ip.AddressUtils;
import com.gbase.admin.common.utils.spring.SpringUtils;
import com.gbase.admin.system.domain.SysOperLog;
import com.gbase.admin.system.service.ISysOperLogService;

import java.util.Date;
import java.util.TimerTask;

/**
 * Async factory
 *
 * @author xielibo
 */
public class AsyncFactory {

    /**
     * Record Operation Log
     *
     * @param operLog operLog
     * @return task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
            operLog.setOperLocation(AddressUtils.getRealAddressByIp(operLog.getOperIp()));
            operLog.setOperTime(new Date());
            SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
