package com.gbase.admin.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gbase.admin.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Role Mapper
 *
 * @author xielibo
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * Query role data by pagination according to conditions
     *
     * @param page    page
     * @param sysRole sysRole
     */
    public IPage<SysRole> selectRoleList(IPage<SysRole> page, @Param("entity") SysRole sysRole);

    /**
     * Query role data based on pagination
     *
     * @param sysRole sysRole
     */
    public List<SysRole> selectRoleList(@Param("entity") SysRole sysRole);

    /**
     * Get the role selection box list according to the user ID
     *
     * @param userId userId
     */
    public List<String> selectRoleListByUserId(Integer userId);

    /**
     * Query roles based on user ID
     *
     * @param userId userId
     */
    public List<SysRole> selectRolePermissionByUserId(Integer userId);

    /**
     * Query role by condition
     *
     * @param queryWrapper queryWrapper
     */
    public SysRole selectRole(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    /**
     * Query role by condition
     *
     * @param queryWrapper queryWrapper
     */
    public List<SysRole> selectRoles(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);
}
