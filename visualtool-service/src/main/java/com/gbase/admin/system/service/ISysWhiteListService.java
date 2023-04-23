package com.gbase.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.system.domain.SysWhiteList;

import java.util.List;

/**
 * White List Service Interface
 *
 * @author xielibo
 */
public interface ISysWhiteListService extends IService<SysWhiteList> {
    IPage<SysWhiteList> selectList(IPage<SysWhiteList> page, SysWhiteList whiteList);

    List<SysWhiteList> selectListAll(SysWhiteList whiteList);

    boolean checkIpExistsInWhiteList(String ip);

    public boolean checkTitleExists(SysWhiteList whiteList);
}
