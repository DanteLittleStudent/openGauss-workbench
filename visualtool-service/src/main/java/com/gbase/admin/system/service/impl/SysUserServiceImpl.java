package com.gbase.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbase.admin.common.constant.UserConstants;
import com.gbase.admin.common.core.domain.entity.SysRole;
import com.gbase.admin.common.core.domain.entity.SysUser;
import com.gbase.admin.common.exception.CustomException;
import com.gbase.admin.common.utils.StringUtils;
import com.gbase.admin.system.domain.SysUserRole;
import com.gbase.admin.system.mapper.SysRoleMapper;
import com.gbase.admin.system.mapper.SysUserMapper;
import com.gbase.admin.system.mapper.SysUserRoleMapper;
import com.gbase.admin.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * User Service
 *
 * @author xielibo
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * Query user page list
     *
     * @param user user param
     * @return page list
     */
    @Override
    public IPage<SysUser> selectUserList(SysUser user, IPage<SysUser> page) {
        IPage<SysUser> result = userMapper.selectUserList(page, user);
        return result;
    }

    /**
     * Query by username
     *
     * @param userName username
     * @return SysUser
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserVo(new QueryWrapper<SysUser>().eq("u.user_name", userName).eq("u.del_flag",0));
    }

    /**
     * get By Id
     *
     * @param userId user ID
     * @return SysUser
     */
    @Override
    public SysUser selectUserById(Integer userId) {
        return userMapper.selectUserVo(new QueryWrapper<SysUser>().eq("u.user_id", userId));
    }

    /**
     * Query the user's role
     *
     * @param userName username
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        QueryWrapper<SysRole> eq = new QueryWrapper<SysRole>().eq("u.user_name", userName).eq("r.del_flag", UserConstants.UN_DELETE);
        List<SysRole> list = roleMapper.selectRoles(eq);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }
    /**
     * Check if username is unique
     *
     * @param userName username
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.selectCount(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, userName)).intValue();
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Check if phone is unique
     *
     * @param user user
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Integer userId = user.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getPhonenumber, user.getPhonenumber());
        if (StringUtils.isNull(userId)) {
            userId = 1;
        } else {
            queryWrapper.ne(SysUser::getUserId, user.getUserId());
        }

        SysUser info = userMapper.selectOne(queryWrapper);

        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Check if email is unique
     *
     * @param user user
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Integer userId = user.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getEmail, user.getEmail());

        if (StringUtils.isNull(userId)) {
            userId = 1;
        } else {
            queryWrapper.ne(SysUser::getUserId, user.getUserId());
        }

        SysUser info = userMapper.selectOne(queryWrapper);

        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Check if the user allows the operation
     *
     * @param user user
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("Operation super administrator user not allowed");
        }
    }

    /**
     * save user
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {
        int rows = userMapper.insert(user);
        insertUserRole(user);
        return rows;
    }

    /**
     * update user
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user) {
        Integer userId = user.getUserId();
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
        insertUserRole(user);
        return userMapper.updateById(user);
    }

    /**
     * update status
     *
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateById(user);
    }

    /**
     * updat information
     *
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateById(user);
    }

    /**
     * update user avatar
     *
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        boolean result = this.update(new SysUser(), new UpdateWrapper<SysUser>().lambda().set(SysUser::getAvatar, avatar).eq(SysUser::getUserName, userName));
        return result;
    }

    /**
     * reset password
     *
     */
    @Override
    public int resetPwd(SysUser user) {
        return this.updateById(user) ? 1 : 0;
    }

    /**
     * reset password
     *
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        boolean result = this.update(new SysUser(), new UpdateWrapper<SysUser>().lambda().set(SysUser::getPassword, password).set(SysUser::getUpdatePwd, 1).eq(SysUser::getUserName, userName));
        return result ? 1 : 0;
    }

    /**
     * Add user and role association
     *
     */
    public void insertUserRole(SysUser user) {
        Integer roleId = user.getRoleIds();
        if (roleId != null) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            userRoleMapper.insert(ur);
        }
    }

    /**
     * delete by ID
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(Integer userId) {
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
        return userMapper.deleteById(userId);
    }

    /**
     * Batch delete user
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Integer[] userIds) {
        for (Integer userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().in(SysUserRole::getUserId, userIds));
        return userMapper.deleteBatchIds(Arrays.asList(userIds));
    }

}
