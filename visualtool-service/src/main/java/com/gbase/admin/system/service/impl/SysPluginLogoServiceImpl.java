package com.gbase.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbase.admin.system.service.ISysPluginLogoService;
import com.gbase.admin.system.domain.SysPluginLogo;
import com.gbase.admin.system.mapper.SysPluginLogoMapper;
import org.springframework.stereotype.Service;

/**
 * @author xielibo
 */
@Service
public class SysPluginLogoServiceImpl extends ServiceImpl<SysPluginLogoMapper, SysPluginLogo> implements ISysPluginLogoService {


    @Override
    public void savePluginConfig(String pluginId, String logoPath) {
        LambdaQueryWrapper<SysPluginLogo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPluginLogo::getPluginId, pluginId);
        SysPluginLogo one = this.getOne(queryWrapper);
        if (one == null) {
            one = new SysPluginLogo();
            one.setPluginId(pluginId);
            one.setLogoPath(logoPath);
        }
        this.saveOrUpdate(one);
    }

    @Override
    public SysPluginLogo getByPluginId(String pluginId) {
        LambdaQueryWrapper<SysPluginLogo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPluginLogo::getPluginId, pluginId).last("limit 1");
        SysPluginLogo one = this.getOne(queryWrapper);
        return one;
    }
}
