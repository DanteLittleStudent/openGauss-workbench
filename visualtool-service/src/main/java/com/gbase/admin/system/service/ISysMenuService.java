package com.gbase.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gbase.admin.common.core.domain.TreeSelect;
import com.gbase.admin.common.core.domain.entity.SysMenu;
import com.gbase.admin.common.core.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * Menu Service Interface
 *
 * @author xielibo
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * Query the system menu list according to the user
     *
     * @param userId userId
     */
    public List<SysMenu> selectMenuList(Integer userId);

    /**
     * Query menu list
     *
     * @param menu menu
     * @param userId user ID
     * @return menu list
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Integer userId);

    List<SysMenu> selectSpecialRouteList(Integer openPosition);

    SysMenu existsMenu(Integer parentId, String pluginId, String path, String menuName);

    /**
     * Query permission list based on user ID
     *
     * @param userId user ID
     * @return permission list
     */
    public Set<String> selectMenuPermsByUserId(Integer userId);

    /**
     * Query menu list by user ID
     *
     * @param userId user ID
     * @return menu list
     */
    public List<SysMenu> selectMenuTreeByUserId(Integer userId);

    /**
     * Query menu tree information based on role ID
     *
     * @param roleId role ID
     * @return menu IDs
     */
    public List<Integer> selectMenuListByRoleId(Integer roleId);

    /**
     * The menu needed to build the front-end routing
     *
     * @param menus menu list
     * @param language current locale
     * @return router list
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus, String language);

    /**
     * Build the menu tree structure required by the front end
     *
     * @param menus menus
     * @return menu tree list
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * Build the menu tree select structure required by the front end
     *
     * @param menus menu list
     * @return tree select
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * get by ID
     *
     * @param menuId Menu ID
     * @return menu
     */
    public SysMenu selectMenuById(Integer menuId);

    /**
     * Determine if a menu exists as a submenu
     *
     * @param menuId Menu ID
     */
    public boolean hasChildByMenuId(Integer menuId);

    Integer countMenuHasOtherPluginSubmenuByPluginId(String pluginId);

    /**
     * Determine whether the menu is assigned
     *
     * @param menuId Menu ID
     */
    public boolean checkMenuExistRole(Integer menuId);

    /**
     * save menu
     */
    public int insertMenu(SysMenu menu);

    /**
     * update menu
     */
    public int updateMenu(SysMenu menu);

    /**
     * delete menu
     */
    public int deleteMenuById(Integer menuId);

    int deleteByPluginId(String pluginId);

    /**
     * Check if the menu is unique
     */
    public String checkMenuNameUnique(SysMenu menu);

    void updatePluginMenuTheme(String pluginId, String theme);

    void updatePluginMenuIcon(String pluginId, String icon);
}
