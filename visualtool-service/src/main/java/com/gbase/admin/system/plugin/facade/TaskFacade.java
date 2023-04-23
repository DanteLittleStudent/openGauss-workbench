package com.gbase.admin.system.plugin.facade;

import com.gbase.admin.common.enums.SysTaskStatus;
import com.gbase.admin.common.enums.SysTaskType;
import com.gbase.admin.system.service.ISysTaskService;
import com.gbase.admin.system.domain.SysTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @className: TaskFacade
 * @description: Task service provided to plugins.
 * @author: xielibo
 * @date: 2023-01-13 8:59 PM
 **/
@Service
public class TaskFacade {

    @Autowired
    private ISysTaskService sysTaskService;

    /**
     * Add tasks to the platform task list
     * @param pluginId
     * @return taskId
     */
    public Integer saveTask(String pluginId, String hostId, String taskName, SysTaskType taskType, Integer subTaskCount){
        SysTask task = new SysTask();
        task.setPluginId(pluginId);
        task.setTaskType(taskType.getCode());
        task.setTaskName(taskName);
        task.setExecHostId(hostId);
        task.setCreateTime(new Date());
        task.setExecStatus(SysTaskStatus.CREATED.getCode());
        task.setSubTaskCount(subTaskCount);
        sysTaskService.save(task);
        return task.getId();
    }
}
