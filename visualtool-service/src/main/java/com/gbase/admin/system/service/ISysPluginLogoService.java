package com.gbase.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.system.domain.SysPluginLogo;

/**
 * @author xielibo
 */
public interface ISysPluginLogoService extends IService<SysPluginLogo> {

    public void savePluginConfig(String pluginId, String logoPath);

    SysPluginLogo getByPluginId(String pluginId);
}
