package com.gbase.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gbase.admin.system.domain.SysOperLog;

/**
 * OperLog Service Interface
 *
 * @author xielibo
 */
public interface ISysOperLogService {

    /**
     * Save Operlog
     *
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * Query the collection of system operation logs
     *
     * @param operLog operLog
     */
    public IPage<SysOperLog> selectOperLogList(IPage<SysOperLog> page, SysOperLog operLog);

    /**
     * Delete System Operation Logs in Batches
     *
     * @param operIds operIds
     */
    public int deleteOperLogByIds(String[] operIds);

    /**
     * Query operation log details
     *
     * @param operId operId
     */
    public SysOperLog selectOperLogById(String operId);

    /**
     * clear operation log
     */
    public void cleanOperLog();
}
